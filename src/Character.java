
import javax.swing.ImageIcon;

/**
 *
 * @author Spencer
 */
class Character {
    private String name;
    private int[] features;
    RandomPortrait rp;
    ImageIcon[] iArray;

    Character() {
        features = newFeatures();
        if (features[2] == 1){
        	name = Background.randomBoyName();
        } else {
        	name = Background.randomGirlName();
        }
        //name = randomName(features[2]);//gives male or female name, needs sex info
        rp = new RandomPortrait(features);
        iArray = rp.getImages();

    }

    public String[] getFeatures() {
        return rp.describeFeatures(features);
    }

    public String getName() {
        return name;
    }

    /**
     * an ordered set of semi-transparent images.
     * when displayed in this order will form a coherent portrait.
     * @return an ordered set of semi-transparent images.
     */
    public ImageIcon[] getPortrait(){
    	return iArray;
    }

    /**
     * creates a new set of features from scratch based upon commonly found features in the real world.
     * For instance, girls are rarely bald, and rarely have beards or mustaches.
     * @return an ordered list of the features a character has.
     */
    private int[] newFeatures(){
    	int[] randF;
    	randF = new int[14];
        randF[0] = Background.mt.nextInt(2) + 1;//1 to 2
        randF[1] = Background.mt.nextInt(5) + 1;
        randF[2] = Background.mt.nextInt(2) + 1;
        randF[3] = Background.mt.nextInt(2) + 1;
        randF[4] = Background.mt.nextInt(2) + 1;
        if (randF[2] == 1){//boy
        	randF[5] = Background.mt.nextInt(6) + 1;//are sometimes bald
        	randF[6] = Background.mt.nextInt(2) + 1;
        } else {//girl, none for beard and mustache
            randF[5] = Background.mt.nextInt(5) + 1;//not usually bald
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

        return randF;
    }

}
