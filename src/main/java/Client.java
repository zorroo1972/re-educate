import java.util.Arrays;
import java.util.StringJoiner;

class Client {
    String name, surname, middlename;
    private Client(String name, String surname, String middlename) {
        this.name = name;
        this.surname = surname;
        this.middlename = middlename;
    }
   static class ClientBld{
         String name, surname, middlename;
        public ClientBld setName(String name) {
            if(name != null || name != "") {
                this.name = name;
                return this;
            }
            else {
                throw new RuntimeException("Fill name");
            }
        }
        public ClientBld setMiddlename(String middlename) {
            if(middlename != null || middlename != "") {
                this.middlename = middlename;
                return this;
            }
             else {
                throw new RuntimeException("Fill middlename");
            }
        }
        public ClientBld setSurname(String surname) {
            if(surname != null || surname != "") {
                this.surname = surname;
                return this;
            }
            else {
                throw new RuntimeException("Fill surname");
            }
        }
        public Client build() {
            return new Client(name, surname, middlename);
        }


    }

    @Override
    public String toString() {
        return name+ ' ' + surname+' ' + middlename +  '/';
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }
}
