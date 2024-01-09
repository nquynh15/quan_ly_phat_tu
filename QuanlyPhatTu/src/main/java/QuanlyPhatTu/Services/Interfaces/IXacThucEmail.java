package QuanlyPhatTu.Services.Interfaces;

public interface IXacThucEmail {
    public String sendverificationEmail();
    public boolean verifyAccount(String enteredCode) throws Exception;
}
