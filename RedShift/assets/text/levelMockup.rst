/Level Data Mockup/
[name]{
    	/Specifics/
	[activeArea] {RECTANGLE}
	[warnArea]   {RECTANGLE}
	[spawn]      (POINT) /Where the player spawns/
	[music]      (NAME) /Sound to use for music/

	/triggers/
	[class]		(string for each level object) /check this first/
	[subtype]	()
	[name]		(name)
	[trigType]	(enum trigtype)
	[x]		(x point)
	[y]		(y point)
	

	/NavPoints/
	[class]		(string for each level object)
	[name]		(string)
	[x]		(int)
	[y]		(int)
	[state]		(string) /will evaluate to bool/

	/objectives/
	[class]		(string for each level object)
	[name]		(string)
	[tltip]		(string)
	[desc]		(string)
	[state]		(string)
	

}