 class Currency {
    final public String isoCode;
    final public String name;


    private Currency(String isoCode, String name) {
        this.isoCode = isoCode;
        this.name = name;
    }

     static class CurrBld {
        String isoCode, name;

        public CurrBld setName(String name) {
            this.name = name;
            return this;
        }

        public CurrBld setIsoCode(String isoCode) {
            this.isoCode = isoCode;
            return this;
        }

        public Currency build() {
            return new Currency(isoCode, name);
        }

        public String getIsoCode() {
            return isoCode;
        }

        public String getName() {
            return name;
        }


        @Override
        public String toString() {
            return isoCode;
        }

    }
}
