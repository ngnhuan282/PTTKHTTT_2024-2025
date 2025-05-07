package DTO;

public class NhaCungCapDTO {
		public String maNCC;
		public String tenNCC;
		public String SDT;
		public String diaChi;
		
		
		public NhaCungCapDTO() {
			
		}
		
		public NhaCungCapDTO(String maNCC,String tenNCC, String SDT,String diaChi) {
			this.maNCC=maNCC;
			this.tenNCC=tenNCC;
			this.SDT=SDT;
			this.diaChi=diaChi;
		}
		
		 	public String getMaNCC() { 
			 return this.maNCC; 
			 }

		    public String getTenNCC() { 
		    	return this.tenNCC; 
		    }
		    public String getSDT() {
		    	return this.SDT; 
		    }
		    public String getDiaChi() { 
		    	return this.diaChi; 
		    }
		   

			public void setMaNCC(String maNCC) {
				this.maNCC = maNCC;
			}

			public void setTenNCC(String tenNCC) {
				this.tenNCC = tenNCC;
			}

			public void setSDT(String sDT) {
				this.SDT = sDT;
			}

			public void setDiaChi(String diaChi) {
				this.diaChi = diaChi;
			}

			@Override
			public String toString() {
				return "NhaCungCapDTO [maNCC=" + maNCC + ", tenNCC=" + tenNCC + ", SDT=" + SDT + ", diaChi=" + diaChi
						+ "]";
			}
			
			
	
}
