USE [RestaurantBooking]
GO
/****** Object:  Table [dbo].[Booking]    Script Date: 5/24/2021 1:45:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Booking](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[restaurantId] [int] NOT NULL,
	[userId] [int] NOT NULL,
	[createdDate] [datetime] NULL,
	[bookingDate] [datetime] NOT NULL,
	[persons] [int] NOT NULL,
	[status] [int] NOT NULL,
 CONSTRAINT [pk_Booking] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Food]    Script Date: 5/24/2021 1:45:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Food](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[restaurantId] [int] NOT NULL,
	[typeId] [int] NOT NULL,
	[name] [nvarchar](100) NULL,
	[price] [float] NOT NULL,
 CONSTRAINT [pk_Food] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[FoodType]    Script Date: 5/24/2021 1:45:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[FoodType](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[type] [nvarchar](20) NOT NULL,
 CONSTRAINT [pk_FoodType] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Image]    Script Date: 5/24/2021 1:45:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Image](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[image] [varchar](255) NOT NULL,
 CONSTRAINT [pk_Image] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[RecoverPassword]    Script Date: 5/24/2021 1:45:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[RecoverPassword](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[accountId] [int] NOT NULL,
	[token] [uniqueidentifier] NOT NULL,
	[createdDate] [datetime] NULL,
	[state] [bit] NOT NULL,
 CONSTRAINT [pk_RecoverPassword] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Restaurant]    Script Date: 5/24/2021 1:45:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Restaurant](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[restaurantName] [nvarchar](255) NOT NULL,
	[email] [varchar](100) NOT NULL,
	[phone] [varchar](20) NOT NULL,
	[address] [nvarchar](256) NOT NULL,
	[openTime] [time](7) NOT NULL,
	[closeTime] [time](7) NOT NULL,
	[state] [int] NOT NULL,
	[overview] [nvarchar](max) NULL,
	[bookedTimes] [int] NULL,
	[logo] [int] NULL,
 CONSTRAINT [pk_Restaurant] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [uq_Restaurant_Email] UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [uq_Restaurant_Phone] UNIQUE NONCLUSTERED 
(
	[phone] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Roles]    Script Date: 5/24/2021 1:45:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Roles](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name] [nvarchar](50) NOT NULL,
	[role] [varchar](20) NOT NULL,
 CONSTRAINT [pk_Roles] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User]    Script Date: 5/24/2021 1:45:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[firstName] [nvarchar](100) NOT NULL,
	[lastName] [nvarchar](100) NOT NULL,
	[gender] [tinyint] NOT NULL,
	[birthday] [date] NOT NULL,
	[email] [varchar](50) NOT NULL,
	[phone] [varchar](11) NOT NULL,
	[address] [nvarchar](256) NOT NULL,
 CONSTRAINT [pk_User] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [uq_User_Email] UNIQUE NONCLUSTERED 
(
	[email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [uq_User_Phone] UNIQUE NONCLUSTERED 
(
	[phone] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[UserAccount]    Script Date: 5/24/2021 1:45:53 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[UserAccount](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[userId] [int] NULL,
	[restaurantId] [int] NULL,
	[username] [varchar](20) NOT NULL,
	[password] [varchar](60) NOT NULL,
	[roleId] [int] NOT NULL,
	[active] [bit] NOT NULL,
 CONSTRAINT [pk_UserAccount] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
 CONSTRAINT [uq_UserAccount_Username] UNIQUE NONCLUSTERED 
(
	[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Booking] ADD  CONSTRAINT [df_Booking_CreatedDate]  DEFAULT (getdate()) FOR [createdDate]
GO
ALTER TABLE [dbo].[RecoverPassword] ADD  CONSTRAINT [df_RecoverPassword_CreatedDate]  DEFAULT (getdate()) FOR [createdDate]
GO
ALTER TABLE [dbo].[Restaurant] ADD  CONSTRAINT [df_Restaurant_BookedTimes]  DEFAULT ((0)) FOR [bookedTimes]
GO
ALTER TABLE [dbo].[UserAccount] ADD  CONSTRAINT [df_UserAccount_Active]  DEFAULT ((1)) FOR [active]
GO
ALTER TABLE [dbo].[Booking]  WITH CHECK ADD  CONSTRAINT [fk_Booking_Restaurant] FOREIGN KEY([restaurantId])
REFERENCES [dbo].[Restaurant] ([id])
GO
ALTER TABLE [dbo].[Booking] CHECK CONSTRAINT [fk_Booking_Restaurant]
GO
ALTER TABLE [dbo].[Booking]  WITH CHECK ADD  CONSTRAINT [fk_Booking_User] FOREIGN KEY([userId])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[Booking] CHECK CONSTRAINT [fk_Booking_User]
GO
ALTER TABLE [dbo].[Food]  WITH CHECK ADD  CONSTRAINT [fk_Food_FoodType] FOREIGN KEY([typeId])
REFERENCES [dbo].[FoodType] ([id])
GO
ALTER TABLE [dbo].[Food] CHECK CONSTRAINT [fk_Food_FoodType]
GO
ALTER TABLE [dbo].[Food]  WITH CHECK ADD  CONSTRAINT [fk_Food_Restaurant] FOREIGN KEY([restaurantId])
REFERENCES [dbo].[Restaurant] ([id])
GO
ALTER TABLE [dbo].[Food] CHECK CONSTRAINT [fk_Food_Restaurant]
GO
ALTER TABLE [dbo].[Restaurant]  WITH CHECK ADD  CONSTRAINT [pk_Restaurant_Image] FOREIGN KEY([logo])
REFERENCES [dbo].[Image] ([id])
GO
ALTER TABLE [dbo].[Restaurant] CHECK CONSTRAINT [pk_Restaurant_Image]
GO
ALTER TABLE [dbo].[UserAccount]  WITH CHECK ADD  CONSTRAINT [fk_UserAccount_Roles] FOREIGN KEY([roleId])
REFERENCES [dbo].[Roles] ([id])
GO
ALTER TABLE [dbo].[UserAccount] CHECK CONSTRAINT [fk_UserAccount_Roles]
GO
ALTER TABLE [dbo].[UserAccount]  WITH CHECK ADD  CONSTRAINT [fk_UserAccount_User] FOREIGN KEY([userId])
REFERENCES [dbo].[User] ([id])
GO
ALTER TABLE [dbo].[UserAccount] CHECK CONSTRAINT [fk_UserAccount_User]
GO
ALTER TABLE [dbo].[Booking]  WITH CHECK ADD  CONSTRAINT [ck_Booking_Persons] CHECK  (([persons]>(0)))
GO
ALTER TABLE [dbo].[Booking] CHECK CONSTRAINT [ck_Booking_Persons]
GO
ALTER TABLE [dbo].[Booking]  WITH CHECK ADD  CONSTRAINT [ck_Booking_Status] CHECK  (([status]>=(0) AND [status]<=(2)))
GO
ALTER TABLE [dbo].[Booking] CHECK CONSTRAINT [ck_Booking_Status]
GO
ALTER TABLE [dbo].[Restaurant]  WITH CHECK ADD  CONSTRAINT [ck_Restaurant_State] CHECK  (([state]>=(0) AND [state]<=(2)))
GO
ALTER TABLE [dbo].[Restaurant] CHECK CONSTRAINT [ck_Restaurant_State]
GO
ALTER TABLE [dbo].[User]  WITH CHECK ADD  CONSTRAINT [ck_User_Gender] CHECK  (([gender]>=(0) AND [gender]<=(2)))
GO
ALTER TABLE [dbo].[User] CHECK CONSTRAINT [ck_User_Gender]
GO
