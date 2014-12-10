class InitMain {
    public static void main (String[] args) {
        System.out.println(new Init().testCases());
    }
}

class Init {
    int aField;

    public int testCases() {
        //Tests correct instances of init before use
        aField = this.correct();
        //Tests incorrect instances of use before init
        aField = this.incorrect();
        
        return 1;
    }

    public int correct() {
        int a;
        int b;
        int c;
        int d;

        //base level initialization
        a = 1;
        aField = a+1;

        //single if/else block init
        if (aField < 0) {
            b = 1;
        } else {
            b = 2;
        }
        aField = b+1;

        //nested initialization if/if/else/else
        if (aField < 0) {
            if (aField < 0) {
                c = 1;
            } else {
                c = 2;
            }
        } else { 
            c = 3;
        }
        aField = c+1;

        //nested initialization if/else/if/else
        if ( aField < 0) {
            d = 1;
        } else {
            if (aField < 0) {
                d = 2;
            } else {
                d = 3;
            }
        }
        aField = d+1;

        return 1;
    }

    public int incorrect() {
        int a;
        int b;
        int c;
        int d;
        int e;

        //Used before initialized base level
        aField = a+1;


        //Initialized only in if
        if (aField < 0) {
            b = 1;
        } else { }
        aField = b+1;

        //Initialized only in else
        if (aField < 0) {
        } else {
            c = 1;
        }
        aField = c+1;

        //Initialized in part of nested if, not in nested else
        if ( aField < 0) {
            if ( aField < 0) {
                d = 1;
            } else { }
        } else {
            d = 2;
        }
        aField = d+1;

        //Initialized in nested if/else, not in else
        if (aField < 0) {
            if (aField < 0) {
                e = 1;
            } else {
                e = 2;
            }
        } else { }
        aField = e+1;

        return 1;
    }
}
