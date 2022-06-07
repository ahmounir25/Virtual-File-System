public class access {

    String userName;
    boolean create;
    boolean delete;

    access(String name,boolean c,boolean d){
        this.userName=name;
        this.create=c;
        this.delete=d;
        if (userName=="admin"){
            this.delete=true;
            this.create=true;
        }
    }

}
