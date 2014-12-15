import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by QueensBase on 08-Dec-14.
 */

public class Database {

    @Table(name = "Users")
    public class Register extends Model {
        // If name is omitted, then the field name is used.
        @Column(name = "Username")
        public String username;

        @Column(name = "Fullname")
        public String fullname;

        @Column(name = "Password")
        public String password;

        @Column(name = "Email")
        public String email;

        @Column(name = "Businessname")
        public String businesname;

        public Register(){
            super();
        }
        public Register(String username, String fullname, String password, String email, String businessname){
            super();
            this.username = username;
            this.fullname = fullname;
            this.password = password;
            this.email = email;
            this.businesname = businessname;
        }
    }
}
