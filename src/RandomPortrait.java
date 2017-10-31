
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import javax.swing.ImageIcon;

/**
 *
 * @author Spencer
 */
public class RandomPortrait {
	String foldername = "images/random/";
    private ImageIcon[] iconArray;
    private boolean done;
    /**
     * constructor to make a portrait with random features
     */
    public RandomPortrait(){
        done = false;
        iconArray = new ImageIcon[10];

        iconArray = randomlyGeneratePictures();
    }

    /**
     * constructor to make a portrait with specific features
     * @param feat a legal list of feature ints
     */
    public RandomPortrait(int[] feat){
    	done = false;
    	iconArray = new ImageIcon[10];
    	iconArray = forciblyGeneratePictures(feat);
    }

    //public accessor method for the array of ImageIcons
    public ImageIcon[] getImages(){
        return iconArray;
    }

    //gets a single layer of the portrait
    public ImageIcon getImageAt(int i){
        if (i < iconArray.length && i > 0){
        return iconArray[i];
        } else return null;
    }

    /**
     * Public method for creating readable String descriptions of features in a corresponding array of ints
     * @param f an int array containing legal feature numbers, beginning at 1, and usually ending in 2, 3, 5, or 10, depending on the feature
     * @return an array of descriptive strings about the corresponding features, arranged in the same order
     */
    public String[] describeFeatures(int[] f){
    	String[] features = new String[14];
    	features[0] = describeSkin(f[0]);
    	features[1] = describeEyes(f[1]);
    	features[2] = describeSex(f[2]);
    	features[3] = describeSmile(f[3]);
    	features[4] = describeLips(f[4]);
    	features[5] = describeHair(f[5]);
    	features[6] = describeBeard(f[6]);
    	features[7] = describeMustache(f[7]);
    	features[8] = describeNose(f[8]);
    	features[9] = describeShirt(f[9]);
    	features[10] = describeHat(f[10]);
    	//features[11] = describeHatStyle(f[11]);
    	features[11] = describeGlasses(f[12]);
    	//features[13] = describeGlassesStyle(f[13]);
    	return features;
    }

    //POSSIBLY UNNECESSARY
    //flag to tell when the portrait has been successfully generated
    public boolean isDone(){
        return done;
    }

    //----------------------------------------------------------------
/**
 * Creates an int[] of random integers corresponding to the parity values of each feature.
 * Runs forciblyGeneratePictures() based on this array.
 * @return an array of ImageIcons (in the proper order) of randomly assembled portrait features.
 */
    private ImageIcon[] randomlyGeneratePictures() {
    	int[] randF;
    	randF = new int[14];
        randF[0] = Background.mt.nextInt(2) + 1;//1 to 2
        randF[1] = Background.mt.nextInt(5) + 1;
        randF[2] = Background.mt.nextInt(2) + 1;
        randF[3] = Background.mt.nextInt(2) + 1;
        randF[4] = Background.mt.nextInt(2) + 1;
        randF[5] = Background.mt.nextInt(6) + 1;
        if (randF[2] == 1){//boy
        randF[6] = Background.mt.nextInt(2) + 1;
        randF[7] = Background.mt.nextInt(2) + 1;
        } else {//girl, none for beard and mustache
        	randF[6] = 2;
        	randF[7] = 2;
        }
        randF[8] = Background.mt.nextInt(3) + 1;
        randF[9] = Background.mt.nextInt(/*10*/6) + 1;
        randF[10] = Background.mt.nextInt(2) + 1;
        if (randF[10] == 1)//hat
        	randF[11] = Background.mt.nextInt(3) + 1;
        else //no hat, none for hat style
        	randF[11] = 4;
        randF[12] = Background.mt.nextInt(2) + 1;
        if (randF[12] == 1)//glasses
        	randF[13] = Background.mt.nextInt(4) + 1;
        else //no glasses, none for glasses style
        	randF[13] = 5;
        return forciblyGeneratePictures(randF);
    }
    /**
     * Creates an ordered array of ImageIcons for a particular set of features
     * @param f an array of integers of proper parity corresponding to desired features
     * @return an ordered array of ImagIcons corresponding to a single portrait.
     */
    private ImageIcon[] forciblyGeneratePictures(int[] f) {
        iconArray[0] = getSkinColor(f[0]);
        iconArray[1] = getEyeColor(f[1]);
        int sex = f[2];
        iconArray[2] = getSex(sex);
        iconArray[3] = getSmile(f[3], f[4]);
        int haircolor = f[5];
        iconArray[4] = getFacialHair(sex, f[6], f[7], haircolor);
        iconArray[5] = getNose(f[8]);
        iconArray[6] = getShirt(f[9]);
        iconArray[7] = getHair(sex, haircolor);
        iconArray[8] = getHat(f[10], f[11]);
        iconArray[9] = getEyewear(f[12], f[13]);
        done = true;
        return iconArray;
    }

    //------------------------------------------------------------------
    //String descriptions of each feature number
    //------------------------------------------------------------------

    private String describeSkin(int f){
    	switch (f){ //skin
    	case 1: return "light skin";//light
    	case 2: return "dark skin";//dark
    	default: return "skin type error";
    	}
    }

    private String describeEyes(int f){
    	switch (f){ //eyes
    	case 1: return "blue eyes";//blue
    	case 2: return "black eyes";//black
    	case 3: return "brown eyes";//brown
    	case 4: return "green eyes";//green
    	case 5: return "grey eyes";//grey
    	default: return "eye color error";
    	}
    }

    private String describeSex(int f){
    	switch (f){ //sex
    	case 1: return"boy";//boy
    	case 2: return "girl";//girl
    	default: return "sex error";
    	}
    }

    private String describeSmile(int f){
    	switch (f){ //smile
    	case 1: return "smiling";//smile
    	case 2: return "frowning";//frown
    	default: return "smile error";
    	}
    }

    private String describeLips(int f){
    	switch (f){ //lips
    	case 1: return"big lips";//big lips
    	case 2: return "thin lips";//thin lips
    	default: return "lips error";
    	}
    }

    private String describeHair(int f){
    	switch (f){ //hair
    	case 1: return "blonde hair";//blonde
    	case 2: return "black hair";//black
    	case 3: return "brown hair";//brown
    	case 4: return "red hair";//red
    	case 5: return "grey hair";//grey
    	case 6: return "bald";//bald
    	default: return "hair color error";
    	}
    }

    private String describeBeard(int f){
    	switch (f){ //beard?
    	case 1: return "beard";//beard
    	case 2: return "no beard";//no beard
    	default: return "beard error";
    	}
    }

    private String describeMustache(int f){
    	switch (f){ //mustache?
    	case 1: return "mustache";//mustache
    	case 2: return "no mustache";//no mustache
    	default: return "mustache error";
    	}
    }

    private String describeNose(int f){
    	switch (f){ //nose type
    	case 1: return "big nose";
    	case 2: return "short nose";
    	case 3: return "thin nose";
    	default: return "nose error";
    	}
    }

    private String describeShirt(int f){
    	switch (f){ //shirt type
    	case 1: return "blue shirt";
    	case 2: return "black shirt";
    	case 3: return "red shirt";
    	case 4: return "green shirt";
    	case 5: return "orange shirt";
    	case 6: return "yellow shirt";
    	/*
    	case 7: return "purple shirt";
    	case 8: return "white shirt";
    	case 9: return "leopard shirt";
    	case 10: return "warning shirt";
    	*/
    	default: return "shirt error";
    	}
    }

    private String describeHat(int f){
    	switch (f){ //hat?
    	case 1: return "hat";
    	case 2: return "no hat";
    	default: return "hat error";
    	}
    }

    /*
    private String describeHatStyle(int f){
    	switch (f){ //hat style
    	case 1: return "baseball cap";
    	case 2: return "cowboy hat";
    	case 3: return "beret";
    	default: return "hat - none";
    	}
    }
    */

    private String describeGlasses(int f){
    	switch (f){ //glasses?
    	case 1: return "glasses";
    	case 2: return "no glasses";
    	default: return "glasses error";
    	}
    }

    /*
    private String describeGlassesStyle(int f){
    	switch (f){ //glasses style
    	case 1: return "thin glasses";
    	case 2: return "thick glasses";
    	case 3: return "eyepatch";
    	case 4: return "monocle";
    	default: return "glasses - none";
    	}
    }
    */


    //--------------------------------------------------------
    //GETS THE IMAGE FILES FOR EACH RESPECTIVE FEATURE NUMBER IN THE ARRAY
    //--------------------------------------------------------
    private ImageIcon getSkinColor(int i) {
    	int kind = Background.mt.nextInt(3) + 1;
        if (i == 1) {
        	switch (kind){
        	case 0: return new ImageIcon(foldername + "lightskin1.png");
        	case 1: return new ImageIcon(foldername + "lightskin2.png");
        	default: return new ImageIcon(foldername + "lightskin3.png");
        	}
        } else {
        	switch (kind){
    	case 0: return new ImageIcon(foldername + "darkskin1.png");
    	case 1: return new ImageIcon(foldername + "darkskin2.png");
    	default: return new ImageIcon(foldername + "darkskin3.png");
    	}
        }
    }

    private ImageIcon getNose(int i) {
        if (i == 1) {
            return new ImageIcon(foldername + "bignose.png");
        } else if (i == 2) {
            return new ImageIcon(foldername + "shortnose.png");
        } else {
            return new ImageIcon(foldername + "thinnose.png");
        }
    }

    private ImageIcon getEyeColor(int i) {
        switch (i) {
            case 1:
                return new ImageIcon(foldername + "blueeyes.png");
            case 2:
                return new ImageIcon(foldername + "blackeyes.png");
            case 3:
                return new ImageIcon(foldername + "browneyes.png");
            case 4:
                return new ImageIcon(foldername + "greeneyes.png");
            case 5:
                return new ImageIcon(foldername + "greyeyes.png");
        }
        return new ImageIcon(foldername + "blank.png");
    }

    private ImageIcon getSex(int i) {
    	int kind = Background.mt.nextInt(2) + 1;
        if (i == 1) {
        	switch (kind){
        	case 1: return new ImageIcon(foldername + "boy.png");
        	default:return new ImageIcon(foldername + "boy2.png");
        	}

        } else {
        	switch (kind){
        	case 1: return new ImageIcon(foldername + "girl.png");
        	default: return new ImageIcon(foldername + "girl2.png");
        	}
        }
    }

    private ImageIcon getSmile(int i, int j) {
        if (i == 1) {
            if (j == 1) {
                return new ImageIcon(foldername + "bigsmile.png");
            } else {
                return new ImageIcon(foldername + "smallsmile.png");
            }
        } else {
            if (j == 1) {
                return new ImageIcon(foldername + "bigfrown.png");
            } else {
                return new ImageIcon(foldername + "smallfrown.png");
            }
        }
    }

    private ImageIcon getFacialHair(int s, int i, int j, int k) {
        if (s != 2){//only men have beards or mustaches, usually
        if (i == 1) {//has a beard
            switch (k) {
                case 1:
                    return new ImageIcon(foldername + "blondebeard.png");
                case 2:
                    return new ImageIcon(foldername + "blackbeard.png");
                case 3:
                    return new ImageIcon(foldername + "brownbeard.png");
                case 4:
                    return new ImageIcon(foldername + "redbeard.png");
                case 5:
                    return new ImageIcon(foldername + "greybeard.png");
            }
        } else if (j == 1) {//has mustache
            switch (k) {
                case 1:
                    return new ImageIcon(foldername + "blondemustache.png");
                case 2:
                    return new ImageIcon(foldername + "blackmustache.png");
                case 3:
                    return new ImageIcon(foldername + "brownmustache.png");
                case 4:
                    return new ImageIcon(foldername + "redmustache.png");
                case 5:
                    return new ImageIcon(foldername + "greymustache.png");
            }
        }
        }
        return new ImageIcon(foldername + "blank.png");

    }

    private ImageIcon getShirt(int i) {
        switch (i) {
            case 1:
                return new ImageIcon(foldername + "blueshirt.png");
            case 2:
                return new ImageIcon(foldername + "blackshirt.png");
            case 3:
                return new ImageIcon(foldername + "redshirt.png");
            case 4:
                return new ImageIcon(foldername + "greenshirt.png");
            case 5:
                return new ImageIcon(foldername + "orangeshirt.png");
            case 6:
                return new ImageIcon(foldername + "yellowshirt.png");
            case 7:
                return new ImageIcon(foldername + "purpleshirt.png");
            case 8:
                return new ImageIcon(foldername + "whiteshirt.png");
            case 9:
                return new ImageIcon(foldername + "leopardshirt.png");
            case 10:
                return new ImageIcon(foldername + "warningshirt.png");
        }
        return new ImageIcon(foldername + "blank.png");
    }

    private ImageIcon getHair(int i, int j) {
        //blonde(1), black(2), brown(3), red(4), or grey(5)?
        if (i == 1) {//boy
            switch (j) {
                case 1:
                    return new ImageIcon(foldername + "blondehairboy.png");
                case 2:
                    return new ImageIcon(foldername + "blackhairboy.png");
                case 3:
                    return new ImageIcon(foldername + "brownhairboy.png");
                case 4:
                    return new ImageIcon(foldername + "redhairboy.png");
                case 5:
                    return new ImageIcon(foldername + "greyhairboy.png");
                case 6:
                	return new ImageIcon(foldername + "blank.png");
            }
        } else {//girl
            switch (j) {
                case 1:
                    return new ImageIcon(foldername + "blondehairgirl.png");
                case 2:
                    return new ImageIcon(foldername + "blackhairgirl.png");
                case 3:
                    return new ImageIcon(foldername + "brownhairgirl.png");
                case 4:
                    return new ImageIcon(foldername + "redhairgirl.png");
                case 5:
                    return new ImageIcon(foldername + "greyhairgirl.png");
            }
        }
        return new ImageIcon(foldername + "blank.png");
    }

    private ImageIcon getHat(int i, int j) {
        if (i == 1) {//hat
            switch (j) {
                case 1:
                    return new ImageIcon(foldername + "baseballcap.png");
                case 2:
                    return new ImageIcon(foldername + "cowboyhat.png");
                case 3:
                    return new ImageIcon(foldername + "beret.png");
            }
        } //no hat
        return new ImageIcon(foldername + "blank.png");
    }

    private ImageIcon getEyewear(int i, int j) {
        if (i == 1) {//eyewear
            switch (j) {
                case 1:
                    return new ImageIcon(foldername + "thinglasses.png");
                case 2:
                    return new ImageIcon(foldername + "thickglasses.png");
                case 3:
                    return new ImageIcon(foldername + "eyepatch.png");
                case 4:
                    return new ImageIcon(foldername + "monocle.png");
            }
        } //no eyewear
        return new ImageIcon(foldername + "blank.png");
    }

}

//TO-DO: FIX JLISTS TO REPRESENT NEW FEATURE_SPACE
