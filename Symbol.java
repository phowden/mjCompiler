public class Symbol {

    private String name;
    private ValueType type;

    private MJClass fieldOf;
    private Method localOf;

    public Symbol(String n, ValueType t, MJClass f) {
        this(n,t,f,null);
    }

    public Symbol(String n, ValueType t, Method m) {
        this(n,t,null,m);
    }

    public Symbol(String n, ValueType t) {
        this(n,t,null,null);
    }

    public Symbol(String n, ValueType t, MJClass f, Method m) {
        this.name = n;
        this.type = t;
        this.fieldOf = f;
        this.localOf = m;
    }
    
    public String getName() {
        return this.name;
    }

    public ValueType getType() {
        return this.type;
    }

    public MJClass getClassBelongsTo() {
        return this.fieldOf;
    }

    public boolean isField() {
        return this.fieldOf != null;
    }

    public Method getMethodBelongsTo() {
        return this.localOf;
    }

    public boolean isLocal() {
        return this.localOf != null;
    }

    public String toString() {
        return type.toString() + " " + name;
    }

    public String toLongString() {
        return type.toString() + " " + name + ((fieldOf != null) ? " field of " + fieldOf.getName() : " local of " + localOf.getName());
    }

    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (!(object instanceof Symbol)) {
            return false;
        }
        Symbol s = (Symbol)object;
        
        if (!this.getName().equals(s.getName())) {
            return false;
        }

        if (!this.getType().equals(s.getType())) {
            return false;
        }

        if (this.isLocal()) {
            if(!s.isLocal()) {
                return false;
            } else {
                //Check equal methods
                Method thisMethod = this.getMethodBelongsTo();
                Method sMethod = s.getMethodBelongsTo();
                if (!thisMethod.equals(sMethod)) {
                    return false;
                }
            }
        }

        if (this.isField()) {
            if(!s.isField()) {
                return false;
            } else {
                //Check equal classes
                MJClass thisClass = this.getClassBelongsTo();
                MJClass sClass = this.getClassBelongsTo();
                if (!thisClass.equals(sClass)) {
                    return false;
                }
            }
        }

        return true;
    }
  }


