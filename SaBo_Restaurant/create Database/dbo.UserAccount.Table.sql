USE [RestaurantBooking]
GO
/****** Object:  Table [dbo].[UserAccount]    Script Date: 5/24/2021 1:47:09 AM ******/
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
ALTER TABLE [dbo].[UserAccount] ADD  CONSTRAINT [df_UserAccount_Active]  DEFAULT ((1)) FOR [active]
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
