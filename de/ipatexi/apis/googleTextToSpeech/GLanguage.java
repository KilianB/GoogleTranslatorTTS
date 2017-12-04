package tts;

public enum GLanguage {
	//TODO find naming convention
	//all allowed language codes can be found here https://cloud.google.com/speech/docs/languages
	Afrikaans("af-za"),
	Amharic("am-et"), 			//አማርኛ (ኢትዮጵያ)
	Armenian("hy-am"),			//Հայ (Հայաստան)
	Azerbaijani("az-az"),		//Azərbaycan 
	Bahasa_Indonesia("id-id"),
	Bahasa_Melayu("ms-my"),
	Bengali("bn-db"), 			//বাংলা (বাংলাদেশ)
	Bengali_India("bn-in"), 	//বাংলা (ভারত)
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
	Georgian("ka-ge"), 				//ქართული (საქართველო)	
	Gujarati("gu-in"), 				//ગુજરાતી (ભારત)
	Croatian("hr-hr"),
	Zulu("zu-za"),
	Island("is-is"),
	Italian("it-it"),
	Javanese("jv-id"),
	Kannada("kn-in"),				//ಕನ್ನಡ (ಭಾರತ) 	India
	Khmer("	km-KH"),				//ភាសាខ្មែរ (កម្ពុជា)	Kambodia
	Lao("lo-lA"),					//ລາວ (ລາວ)	
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
	Tamil_India("ta-in"),			//தமிழ் (இந்தியா)
	Tamil_Singapore("ty-sg"),		//தமிழ் (சிங்கப்பூர்)
	Tamil_SriLanka("ta-lk"),		//தமிழ் (இலங்கை)
	Tamil_Malaysia("ty-my"),		//தமிழ் (மலேசியா)
	Telugu("te-in"),				//తెలుగు (భారతదేశం)	
	Vietnamese("vi-vn"),
	Turkish("tr-tr"),
	Urdu_Pakistan("ur-pk"),			//اردو (پاکستان)
	Urdu_India("ur-in"),			//اردو (بھارت)
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
