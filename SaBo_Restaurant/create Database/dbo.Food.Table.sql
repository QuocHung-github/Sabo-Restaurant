USE [RestaurantBooking]
GO
/****** Object:  Table [dbo].[Food]    Script Date: 5/24/2021 1:47:09 AM ******/
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
