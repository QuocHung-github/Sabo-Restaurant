USE [RestaurantBooking]
GO
/****** Object:  Table [dbo].[FoodType]    Script Date: 5/24/2021 1:47:09 AM ******/
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
