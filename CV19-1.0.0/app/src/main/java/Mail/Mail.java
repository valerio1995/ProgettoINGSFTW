package Mail;

import android.content.Intent;
import android.widget.Toast;

public class Mail {

    public static int SendMail(String mail) {

        Intent i = new Intent(Intent.ACTION_SENDTO);
        //i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , mail);
        i.putExtra(i.EXTRA_CC, mail);
        i.putExtra(i.EXTRA_BCC, mail);

        i.putExtra(Intent.EXTRA_SUBJECT, "Registrazione a CV19");
        i.putExtra(Intent.EXTRA_TEXT   , "Complimenti la registrazione è avvenuta con successo!");
        try {
            return 1;
        } catch (Exception ex) {
            return 0;
        }
    }
}
