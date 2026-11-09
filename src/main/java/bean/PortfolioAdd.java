package bean;

public class PortfolioAdd {

    private long portfolioId;
    private String workName;
    private String description;
    private String fileUrl;
    private long candidateAccountId;

    // getter・setter
    // ポートフォリオID
    public long getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(long portfolioId) {
        this.portfolioId = portfolioId;
    }
    //作品名
    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }
    //説明文
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    //ファイル保存先URL
    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    //求職者アカウントID
    public long getCandidateAccountId() {
        return candidateAccountId;
    }

    public void setCandidateAccountId(long candidateAccountId) {
        this.candidateAccountId = candidateAccountId;
    }
}