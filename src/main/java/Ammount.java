 class Ammount {
     private Currency cur;
     private int ammount;

     private Ammount(Currency cur, int ammount) {
         this.cur = cur;
         this.ammount = ammount;
     }

     @Override
     public String toString() {
         return ammount + cur.isoCode;
     }
     static class AmmountBld {
         Currency cur;
         int ammount;
         public AmmountBld setAmmount (int ammount) {
             if (ammount >= 0) {
                 this.ammount = ammount;
                 return this;
             }
             else {
                 throw new RuntimeException("Ammount  must be positive");
             }
         }

         public AmmountBld setCurrency (Currency cur){
             this.cur = cur;
             return this;
         }

             public Ammount build () {
                 return new Ammount(cur, ammount);
             }

         }
     }

