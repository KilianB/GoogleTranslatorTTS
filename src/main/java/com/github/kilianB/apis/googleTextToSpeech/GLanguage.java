package com.github.kilianB.apis.googleTextToSpeech;

public enum GLanguage {
	//TODO find naming convention
	//all allowed language codes can be found here https://cloud.google.com/speech/docs/languages
	Afrikaans("af-za"),
	Amharic("am-et"), 			//áŠ áˆ›áˆ­áŠ› (áŠ¢á‰µá‹®áŒµá‹«)
	Armenian("hy-am"),			//Õ€Õ¡Õµ (Õ€Õ¡ÕµÕ¡Õ½Õ¿Õ¡Õ¶)
	Azerbaijani("az-az"),		//AzÉ™rbaycan 
	Bahasa_Indonesia("id-id"),
	Bahasa_Melayu("ms-my"),
	Bengali("bn-db"), 			//à¦¬à¦¾à¦‚à¦²à¦¾ (à¦¬à¦¾à¦‚à¦²à¦¾à¦¦à§‡à¦¶)
	Bengali_India("bn-in"), 	//à¦¬à¦¾à¦‚à¦²à¦¾ (à¦­à¦¾à¦°à¦¤)
	Catalan("ca-es"),
	Czech("cs-cz"),
	Dansk("da-dk"),
	German("de-de"),
	English_AU("en-au"),
	English_CA("en-ca"),
	English_Ghana("en-gh"),
	English_GB("en-gb"),
	English_IN("en-in"),
	English_IR("en-ie"),
	English_Kenya("em-ke"),
	English_NZ("en-nz"),
	English_Nigeria("en-ng"),
	English_Phillipines("en-ph"),
	English_South_Afirca("en-za"),
	English_Tanzania("en-tz"),
	English_US("en-us"),
	Spanish_Argentina("es-ar"),
	Spanish_Bolivia("es-BO"),
	Spanish_Chile("es-cl"),
	Spanish_Colombia("es-co"),
	Spanish_CostaRica("es-cr"),
	Spanish_Ecuador("es-eg"),
	Spanish_ElSalvador("es-sv"),
	Spanish("es-es"),
	Spanish_US("es-us"),
	Spanish_Guatemala("es-gt"),
	Spanish_Honduras("es-hn"),
	Spanish_Mexico("es-mx"),
	Spanish_Nicaragua("es-ni"),
	Spanish_Panama("es-pa"),
	Spanish_Paraguay("es-py"),
	Spanish_Peru("es-pe"),
	Spanish_PuertoRico("es-pr"),
	Spanish_DominicanRepublic("es-do"),
	Spanish_Uruguay("es-uy"),
	Spanish_Venezuela("es-ve"),
	Basque("es-es"),
	Filipino("fil-ph"),
	French_Canada("fr-ca"),
	French("fr-fr"),
	Galician("el-es"),
	Georgian("ka-ge"), 				//áƒ¥áƒ�áƒ áƒ—áƒ£áƒšáƒ˜ (áƒ¡áƒ�áƒ¥áƒ�áƒ áƒ—áƒ•áƒ”áƒšáƒ�)	
	Gujarati("gu-in"), 				//àª—à«�àªœàª°àª¾àª¤à«€ (àª­àª¾àª°àª¤)
	Croatian("hr-hr"),
	Zulu("zu-za"),
	Island("is-is"),
	Italian("it-it"),
	Javanese("jv-id"),
	Kannada("kn-in"),				//à²•à²¨à³�à²¨à²¡ (à²­à²¾à²°à²¤) 	India
	Khmer("	km-KH"),				//áž—áž¶ážŸáž¶áž�áŸ’áž˜áŸ‚ážš (áž€áž˜áŸ’áž–áž»áž‡áž¶)	Kambodia
	Lao("lo-lA"),					//àº¥àº²àº§ (àº¥àº²àº§)	
	Latvian("lv-lv"),
	Lithuanian("lt-lt"),
	Hungarian("hu-hu"),
	Malayalam("ml-in"),
	Marathi("mr-in"),
	Dutch("nl-nl"),
	Nepali("ne-np"),
	Norwegian("nb-no"),
	Polish("pl-pl"),
	Portuguese_Brazil("pt-br"),
	Portuguese("pt-pt"),
	Romanian("ro-ro"),
	Sinhala("si-lk"),
	Slovak("sk-sk"),
	Slovenian("sl-sl"),
	Sundanese("su-id"),
	Swahili_Tanzania("sw-tz"),
	Swahili_Kenya("sw-ke"),
	Finnish("fi-fi"),
	Swedish("sv-se"),
	Tamil_India("ta-in"),			//à®¤à®®à®¿à®´à¯� (à®‡à®¨à¯�à®¤à®¿à®¯à®¾)
	Tamil_Singapore("ty-sg"),		//à®¤à®®à®¿à®´à¯� (à®šà®¿à®™à¯�à®•à®ªà¯�à®ªà¯‚à®°à¯�)
	Tamil_SriLanka("ta-lk"),		//à®¤à®®à®¿à®´à¯� (à®‡à®²à®™à¯�à®•à¯ˆ)
	Tamil_Malaysia("ty-my"),		//à®¤à®®à®¿à®´à¯� (à®®à®²à¯‡à®šà®¿à®¯à®¾)
	Telugu("te-in"),				//à°¤à±†à°²à±�à°—à±� (à°­à°¾à°°à°¤à°¦à±‡à°¶à°‚)	
	Vietnamese("vi-vn"),
	Turkish("tr-tr"),
	Urdu_Pakistan("ur-pk"),			//Ø§Ø±Ø¯Ùˆ (Ù¾Ø§Ú©Ø³ØªØ§Ù†)
	Urdu_India("ur-in"),			//Ø§Ø±Ø¯Ùˆ (Ø¨Ú¾Ø§Ø±Øª)
	Greek("el-gr"),
	Bulgarian("bg-bg"),
	Russian("ru-ru"),
	Serbian("sr-rs"),
	Ukrainia("uk-ua"),
	Hebrew("he-il"),
	Arabic_Isreal("ar-il"),
	Arabic_Jordan("ar-jo"),
	Arabic_UnitedArabEmirates("ar-ae"),
	Arabic_Bahrain("ar-bh"),
	Arabic_Algeria("ar-dz"),
	Arabic_SaudiArabia("ar-sa"),
	Arabic_Iraq("ar-iq"),
	Arabic_Kuwait("ar-kw"),
	Arabic_Morocco("ar-ma"),
	Arabic_Tunisia("ar-tn"),
	Arabic_Oman("ar-om"),
	Arabic_StateofPalestine("ar-ps"),
	Arabic_Qatar("ar-qa"),
	Arabic_Lebanon("ar-lb"),
	Arabic_Egypt("ar-eg"),
	Persian("fa-ir"),
	Hindi("hi-in"),
	Thai("th-th"),
	Korean("ko-kr"),
	Chinese_MandarinTraditionalTaiwan("cmn-hant-tw"),
	Chinese_CantoneseTraditionalHongKong("yue-hant-hk"),
	Japanese("ja-jp"),
	Chinese_MandarinSimplifiedHongKong("cmn-hans-hk"),
	Chinese_MandarinSimplifiedChina("cmn-hans-vn");
	
	
	
	private String countryCode;
	
	private GLanguage(String value) {
		this.countryCode = value;
	}

	public String getCountryCode() {
		return this.countryCode;
	}
	
}
