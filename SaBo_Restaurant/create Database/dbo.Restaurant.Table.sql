USE [RestaurantBooking]
GO
/****** Object:  Table [dbo].[Restaurant]    Script Date: 5/24/2021 1:47:09 AM ******/
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
ALTER TABLE [dbo].[Restaurant] ADD  CONSTRAINT [df_Restaurant_BookedTimes]  DEFAULT ((0)) FOR [bookedTimes]
GO
ALTER TABLE [dbo].[Restaurant]  WITH CHECK ADD  CONSTRAINT [pk_Restaurant_Image] FOREIGN KEY([logo])
REFERENCES [dbo].[Image] ([id])
GO
ALTER TABLE [dbo].[Restaurant] CHECK CONSTRAINT [pk_Restaurant_Image]
GO
ALTER TABLE [dbo].[Restaurant]  WITH CHECK ADD  CONSTRAINT [ck_Restaurant_State] CHECK  (([state]>=(0) AND [state]<=(2)))
GO
ALTER TABLE [dbo].[Restaurant] CHECK CONSTRAINT [ck_Restaurant_State]
GO
