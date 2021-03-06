USE [RestaurantBooking]
GO
/****** Object:  Table [dbo].[RecoverPassword]    Script Date: 5/24/2021 1:47:09 AM ******/
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
ALTER TABLE [dbo].[RecoverPassword] ADD  CONSTRAINT [df_RecoverPassword_CreatedDate]  DEFAULT (getdate()) FOR [createdDate]
GO
