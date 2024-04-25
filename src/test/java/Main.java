import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MM-dd-yyyy");
        String todate= simpleDateFormat.format(date);
        System.out.println(todate);
    }
}
