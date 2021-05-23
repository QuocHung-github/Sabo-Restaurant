USE [master];
GO
IF NOT EXISTS
(
    SELECT [name]
    FROM sys.sql_logins
    WHERE name = 'auth'
)
    CREATE LOGIN [auth] WITH PASSWORD = '@uth_m@n@ger2019';
GO
DECLARE @kill VARCHAR(8000)= '';  
SELECT @kill = @kill + 'kill ' + CONVERT(VARCHAR(5), session_id) + ';'
FROM sys.dm_exec_sessions
WHERE database_id = DB_ID('RestaurantBooking');
EXEC (@kill);
GO
DROP DATABASE IF EXISTS [RestaurantBooking];
GO

CREATE DATABASE [RestaurantBooking]; 
GO

USE [RestaurantBooking];
GO

CREATE TABLE [Image]
([id]    INT IDENTITY(1, 1), 
 [image] VARCHAR(255) NOT NULL,
);
CREATE TABLE [Roles]
([id]   INT IDENTITY(1, 1), 
 [name] NVARCHAR(50) NOT NULL, 
 [role] VARCHAR(20) NOT NULL
);
CREATE TABLE [Restaurant]
([id]             INT IDENTITY(1, 1), 
 [restaurantName] NVARCHAR(255) NOT NULL, 
 [email]          VARCHAR(100) NOT NULL, 
 [phone]          VARCHAR(20) NOT NULL, 
 [address]        NVARCHAR(256) NOT NULL, 
 [openTime]       TIME NOT NULL, 
 [closeTime]      TIME NOT NULL, 
 [state]          INT NOT NULL, 
 [overview]       NVARCHAR(MAX), 
 [bookedTimes]    INT,
 [logo]           INT
);

--CREATE TABLE [Comment]
--([id]           INT IDENTITY(1, 1), 
-- [restaurantId] INT NOT NULL, 
-- [comment]      NVARCHAR(256) NOT NULL, 
-- [rank]         TINYINT NOT NULL
--);

CREATE TABLE [FoodType]
([id]   INT IDENTITY(1, 1), 
 [type] NVARCHAR(20) NOT NULL
);
CREATE TABLE [Food]
([id]           INT IDENTITY(1, 1), 
 [restaurantId] INT NOT NULL, 
 [typeId]       INT NOT NULL, 
 [name]         NVARCHAR(100), 
 [price]        FLOAT NOT NULL
);
CREATE TABLE [User]
([id]           INT IDENTITY(1, 1), 
 [firstName]    NVARCHAR(100) NOT NULL, 
 [lastName]     NVARCHAR(100) NOT NULL, 
 [gender]       tinyint NOT NULL, 
 [birthday]     DATE NOT NULL, 
 [email]        VARCHAR(50) NOT NULL, 
 [phone]        VARCHAR(11) NOT NULL, 
 [address]      NVARCHAR(256) NOT NULL
-- [active]       BIT NOT NULL
);
CREATE TABLE [UserAccount]
([id]       INT IDENTITY(1, 1), 
 [userId]   INT, 
 [restaurantId] int,
 [username] VARCHAR(20) NOT NULL, 
 [password] VARCHAR(60) NOT NULL, 
 [roleId] int NOT NULL,
 [active]   BIT NOT NULL
);
CREATE TABLE [Booking]
([id]           INT IDENTITY(1, 1), 
 [restaurantId] INT NOT NULL, 
 [userId]       INT NOT NULL, 
 [createdDate]  DATETIME, 
 [bookingDate]  DATETIME NOT NULL, 
 [persons]      INT NOT NULL,
 [status]       INT NOT NULL
);
GO

CREATE TABLE [RecoverPassword] 
(
	[id] int IDENTITY(1,1),
	[accountId] int NOT NULL,
	[token] uniqueidentifier NOT NULL,
	[createdDate] datetime,
	[state] bit NOT NULL -- 1 là đã dùng, 0 là chưa
)

--Thiết lập bảng
ALTER TABLE [dbo].[Image]
ADD CONSTRAINT [pk_Image] PRIMARY KEY([id]);
GO
ALTER TABLE [dbo].[Roles]
ADD CONSTRAINT [pk_Roles] PRIMARY KEY([id]);
GO
ALTER TABLE [dbo].[Restaurant]
ADD CONSTRAINT [pk_Restaurant] PRIMARY KEY([id]), 
    CONSTRAINT [uq_Restaurant_Email] UNIQUE([email]), 
    CONSTRAINT [uq_Restaurant_Phone] UNIQUE([phone]), 
    CONSTRAINT [pk_Restaurant_Image] FOREIGN KEY([logo]) REFERENCES [dbo].[Image]([id]), 
	CONSTRAINT [df_Restaurant_BookedTimes] DEFAULT 0 FOR [bookedTimes],
    CONSTRAINT [ck_Restaurant_State] CHECK([state] >= 0
                                           AND [state] <= 2); --0 là đóng cửa, 1 là mở cửa, 2 là tạm ngưng

--ALTER TABLE [dbo].[Comment]
--ADD CONSTRAINT [pk_Comment] PRIMARY KEY([id]), 
--    CONSTRAINT [fk_Comment_Restaurant] FOREIGN KEY([restaurantId]) REFERENCES [dbo].[Restaurant]([id]), 
--    CONSTRAINT [ck_Comment_Rank] CHECK([rank] >= 0
--                                       AND [rank] <= 5);
GO
ALTER TABLE [dbo].[FoodType]
ADD CONSTRAINT [pk_FoodType] PRIMARY KEY([id]);
GO
ALTER TABLE [dbo].[Food]
ADD CONSTRAINT [pk_Food] PRIMARY KEY([id]), 
    CONSTRAINT [fk_Food_FoodType] FOREIGN KEY([typeId]) REFERENCES [dbo].[FoodType]([id]), 
    CONSTRAINT [fk_Food_Restaurant] FOREIGN KEY([restaurantId]) REFERENCES [dbo].[Restaurant]([id])
GO
ALTER TABLE [dbo].[User]
ADD CONSTRAINT [pk_User] PRIMARY KEY([id]), 
    CONSTRAINT [uq_User_Email] UNIQUE([email]), 
    CONSTRAINT [uq_User_Phone] UNIQUE([phone]), 
    CONSTRAINT [ck_User_Gender] CHECK([gender] >= 0 AND [gender] <= 2) -- 0 là nữ, 1 là nam, 2 là khác
    --CONSTRAINT [df_User_Active] DEFAULT 1 FOR [active];
GO
ALTER TABLE [dbo].[UserAccount]
ADD CONSTRAINT [pk_UserAccount] PRIMARY KEY([id]), 
    CONSTRAINT [fk_UserAccount_User] FOREIGN KEY([userId]) REFERENCES [dbo].[User]([id]), 
    CONSTRAINT [uq_UserAccount_Username] UNIQUE([username]), 
	CONSTRAINT [fk_UserAccount_Roles] FOREIGN KEY([roleId]) REFERENCES [dbo].[Roles]([id]), 
    CONSTRAINT [df_UserAccount_Active] DEFAULT 1 FOR [active];
	GO
ALTER TABLE [dbo].[Booking]
ADD CONSTRAINT [pk_Booking] PRIMARY KEY([id]), 
    CONSTRAINT [fk_Booking_User] FOREIGN KEY([userId]) REFERENCES [dbo].[User]([id]), 
    CONSTRAINT [fk_Booking_Restaurant] FOREIGN KEY([restaurantId]) REFERENCES [dbo].[Restaurant]([id]), 
    CONSTRAINT [df_Booking_CreatedDate] DEFAULT GETDATE() FOR [createdDate], 
    CONSTRAINT [ck_Booking_Status] CHECK([status] >= 0
                                         AND [status] <= 2),
    CONSTRAINT [ck_Booking_Persons] CHECK ([persons] > 0);
GO
ALTER TABLE [dbo].[RecoverPassword]
ADD CONSTRAINT [pk_RecoverPassword] PRIMARY KEY ([id]),
CONSTRAINT [df_RecoverPassword_CreatedDate] DEFAULT GETDATE() FOR [createdDate]
GO

CREATE TRIGGER TR_Booking_BookCount ON [Booking]
AFTER INSERT 
AS
BEGIN
	UPDATE [dbo].[Restaurant]
	SET [bookedTimes] = [bookedTimes] + 1 -- INT
	FROM INSERTED i
	WHERE [dbo].[Restaurant].id = i.restaurantId;
END
GO

--Dummy data:
INSERT INTO [dbo].[Image]
(
--id - column value is auto-generated
[image]
)
VALUES
(
-- id - int
'NhaHangTIKI.jpg' -- image - varchar
),
(
-- id - int
'NhaHangVuonChuoi.jpg' -- image - varchar
);
INSERT INTO [dbo].[Roles]
(
--id - column value is auto-generated
name, 
role
)
VALUES
(
-- id - int
N'Chủ cửa hàng', -- name - nvarchar
'MERCHANT' -- role - varchar
),
(
-- id - int
N'Khách hàng', -- name - nvarchar
'CUSTOMER' -- role - varchar
),
(
-- id - int
N'Quản lý', -- name - nvarchar
'ADMIN' -- role - varchar
);

INSERT INTO [dbo].[Restaurant]
(
--id - column value is auto-generated
restaurantName, 
email, 
phone, 
address, 
openTime, 
closeTime, 
state, 
overview, 
logo
)
VALUES
(
-- id - int
N'Nhà hàng tiện lợi TIKI', -- restaurantName - nvarchar
'tikirestaurant@gmail.com', -- email - varchar
'0979222345', -- phone - varchar
N'256 Dương Quảng Hàm, Phường 5, Quận Gò Vấp', 
'9:00', 
'21:30', 
1, 
N'Tổng quan về nhà hàng TIKI', -- overview - nvarchar
1 -- logo - int
),
(
-- id - int
N'Nhà hàng Vườn Chuối', -- restaurantName - nvarchar
'vuonchuoi@gmail.com', -- email - varchar
'0125444334', -- phone - varchar
N'25A Tây Thới, Phường 7, Quận Phú Nhuận', 
'9:00', 
'21:30', 
1, 
N'Tổng quan về nhà hàng Vườn Chuối', -- overview - nvarchar
2 -- logo - int
);

INSERT INTO dbo.FoodType
(
    --id - column value is auto-generated
    type
)
VALUES
(
    -- id - INT
    N'Khai vị' -- type - NVARCHAR
),
(
    -- id - INT
    N'Món chính' -- type - NVARCHAR
),
(
    -- id - INT
    N'Đồ uống' -- type - NVARCHAR
)

INSERT INTO dbo.Food
(
    --id - column value is auto-generated
    restaurantId,
    typeId,
    name,
    price
)
VALUES
(
    -- id - INT
    1, -- restaurantId - INT
    1, -- typeId - INT
    N'', -- name - NVARCHAR
    0.0 -- price - FLOAT
)

INSERT INTO [dbo].[User]
(
--id - column value is auto-generated
firstName, 
lastName, 
gender, 
birthday, 
email, 
phone, 
address
--active
)
VALUES
(
-- id - int
N'Thịnh', -- firstName - nvarchar
N'Trần', -- lastName - nvarchar
1, -- gender - bit
'1967-03-04', -- birthday - date
'tranmanager@gmail.com', -- email - varchar
'0909771883', -- phone - varchar
N'23A Ngô Đức Kế, Phường 7, Quận Tân Bình' -- address - int
--1 -- active - bit
),
(
-- id - int
N'Như', -- firstName - nvarchar
N'Nguyễn', -- lastName - nvarchar
0, -- gender - bit
'1999-09-13', -- birthday - date
'nhu9x@gmail.com', -- email - varchar
'0978665412', -- phone - varchar
N'108/7A Nguyễn Bỉnh Khiêm' -- address - int
--1 -- active - bit

);
INSERT INTO [dbo].[UserAccount]
(
--id - column value is auto-generated
userId, 
restaurantId,
username, 
password,
roleId, 
active
)
VALUES
(
-- id - int
1, -- userId - int
NULL, --restaurantId - int
'manager', -- username - varchar
'$2y$12$/NiSZDUZe1Z2x4PItIBvve.VAGgcpy1PHj/nGI6SaDQ.DFmUSaIwK', -- password - varchar 123
3,
1 -- active - bit
),
(
-- id - int
2, -- userId - int
NULL, --restaurantId - int
'nhu', -- username - varchar
'$2y$12$EDaBzS5lzjHAwmA3bQwPwe70QDj3ZJFBRLwc.rJtMxGwZSk7mhdx.', -- password - varchar 123
2,
1 -- active - bit
),
(
    NULL, --userId - int
    1, -- restaurantId - int
    'mai', -- username - VARCHAR
    '$2y$12$ayObvo6DrLSBJEr/71GcfOipT2bjso0zlpBfRIzviAVH9s05j3b0G', -- password - VARCHAR
	1,
    1 -- active - BIT
),
(
	NULL, -- userId - int
    2, -- restaurantId - int
    'thuy', -- username - VARCHAR
    '$2y$12$7ma0hjQJQYE8e3rUGTV3iuu.eEUieN3EmKkDSErir3vhC7UhlGYoe', -- password - VARCHAR
    1, -- roleId - INT
    1 -- active - BIT
)
