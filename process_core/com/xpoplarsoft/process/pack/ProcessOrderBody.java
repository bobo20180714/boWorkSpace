package com.xpoplarsoft.process.pack;

public class ProcessOrderBody extends ProcessBody {
		//指令代码2字节
		private int orderCode;
		
		//附加内容长度2字节
		private int length;
		//附加内容
		private String content;
		
		public String toString() {
			StringBuilder strSB = new StringBuilder();
			strSB.append("messageId=[").append(this.getMessageId()).append("]");
			strSB.append("satNum=[").append(this.getSatNum()).append("]");
			strSB.append("orderCode=[").append(this.orderCode).append("]");
			strSB.append("sendNum=[").append(this.getSendNum()).append("]");
			strSB.append("length=[").append(this.length).append("]");
			strSB.append("content=[").append(this.content).append("]");
			return strSB.toString();
		}

		public int getOrderCode() {
			return orderCode;
		}

		public void setOrderCode(int orderCode) {
			this.orderCode = orderCode;
		}

		public int getLength() {
			return length;
		}

		public void setLength(int length) {
			this.length = length;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

}
