
/**
 *
 * @author Spencer
 *Used entirely to describe the features used in the JLists found in Background.java.
 *An example of a feature is sex.  It has a parity of 2, because there can be a boy or a girl.
 *boy and  girl would be the options in this case.
 */
class Feature {

//name: beard
//parity: 2
//options = {true, false}
    private String name;
    private int parity;
    private String[] options;
    Feature(String n, String[] o){
        parity = o.length;
        name = n;
        options = o;
    }

    public String getName() {
        return name;
    }

    public String[] getOptions() {
        return options;
    }

    public int getParity() {
        return parity;
    }


}
