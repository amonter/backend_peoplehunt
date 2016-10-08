   package com.crowdscanner.controller.avatars;


public class CharacterFactory {

	public static CharacterMaker retrieveBundleCharacters(Integer bundleId){
		
		CharacterMaker characterMaker = null;
		
		if (bundleId.equals(111)){			
			characterMaker = new KeirseyAlgorithm();

		} else if (bundleId.equals(112)) {
			characterMaker = new DITBundleValidate();
			
		} 	else {
			
			characterMaker = new AvantGardeCharacters();
		}
		
		return characterMaker;
	}	
}
