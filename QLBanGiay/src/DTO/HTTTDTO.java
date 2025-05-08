package DTO;

public class HTTTDTO {
    private String maHTTT;
    private String tenHTTT;

    public HTTTDTO() {}
    public HTTTDTO(String maHTTT, String tenHTTT) {
        this.maHTTT = maHTTT;
        this.tenHTTT = tenHTTT;
    }

    public String getMaHTTT() { return maHTTT; }
    public String getTenHTTT() { return tenHTTT; }
    public void setMaHTTT(String maHTTT) { this.maHTTT = maHTTT; }
    public void setTenHTTT(String tenHTTT) { this.tenHTTT = tenHTTT; }

    @Override
    public String toString() {
        return this.tenHTTT; // hoặc return maHTTT + " - " + tenHTTT;
    }

}
