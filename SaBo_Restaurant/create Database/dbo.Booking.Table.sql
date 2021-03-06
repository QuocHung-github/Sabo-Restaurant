USE [RestaurantBooking]
GO
/****** Object:  Table [dbo].[Booking]    Script Date: 5/24/2021 1:47:09 AM ******/
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
ALTER TABLE [dbo].[Booking] ADD  CONSTRAINT [df_Booking_CreatedDate]  DEFAULT (getdate()) FOR [createdDate]
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
ALTER TABLE [dbo].[Booking]  WITH CHECK ADD  CONSTRAINT [ck_Booking_Persons] CHECK  (([persons]>(0)))
GO
ALTER TABLE [dbo].[Booking] CHECK CONSTRAINT [ck_Booking_Persons]
GO
ALTER TABLE [dbo].[Booking]  WITH CHECK ADD  CONSTRAINT [ck_Booking_Status] CHECK  (([status]>=(0) AND [status]<=(2)))
GO
ALTER TABLE [dbo].[Booking] CHECK CONSTRAINT [ck_Booking_Status]
GO
