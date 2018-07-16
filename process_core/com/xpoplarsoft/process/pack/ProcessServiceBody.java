package com.xpoplarsoft.process.pack;

public class ProcessServiceBody extends ProcessBody {
		//业务代码8字节
		private String serviceCode;
		//附加内容长度2字节
		private int length;
		//附加内容
		private String content;
		
		public String toString() {
			StringBuilder strSB = new StringBuilder();
			strSB.append("messageId=[").append(this.getMessageId()).append("]");
			strSB.append("satNum=[").append(this.getSatNum()).append("]");
			strSB.append("serviceCode=[").append(this.serviceCode).append("]");
			strSB.append("sendNum=[").append(this.getSendNum()).append("]");
			strSB.append("length=[").append(this.length).append("]");
			strSB.append("content=[").append(this.content).append("]");
			return strSB.toString();
		}

		public String getServiceCode() {
			return serviceCode;
		}

		public void setServiceCode(String serviceCode) {
			this.serviceCode = serviceCode;
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
