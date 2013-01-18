/ ENGINES 
	[className]{	
		[name]	      (STRING) the display name
		[tltip]       (STRING) the tooltip string
		[cost]        (INT)    Price of the engine
		[weight]      (INT)    How heavy the engine is
		[turnrate]    (FLOAT)  Turn rate
		[thrustx]     (FLOAT)  Forward thrust
		[thrusty]     (FLOAT)  Reverse thrust (?)
		[strafeRate]  (FLOAT)  Sideways thrust (?)
		[img]         (STRING) What image to use
		[primeThrst]  (STRING) sfx to use for main thrust
		[sideThrst]   (STRING) sfx to use for side thrust
	}
/

[smallEngine]{
	[name]	      (Thiokol 15b)
	[tltip]       (light weight engine, good for strafing)
	[cost]        (1)
	[weight]      (1500)
	[turnrate]    (0.3)
	[thrustx]     (0.2)
	[thrusty]     (0.1)
	[strafeRate]  (0.5)
	[img]         (eng1)
	[wireframe]	(thiokWire)
	[primeThrst]  (test)
	[sideThrst]   (sidethrust)
	[thrstprtcl] (smlEngPrt)
}

[medEngine]{
	[name]	      (McDonnel OAMS-25)
	[tltip]       (middle grade motor; average handling)
	[cost]        (2)
	[weight]      (1996)
	[turnrate]    (0.2)
	[thrustx]     (0.3)
	[thrusty]     (0.1)
	[strafeRate]  (0.2)
	[img]         (mcDon)
	[wireframe]	(mcDonWire)
	[primeThrst]  (test)
	[sideThrst]   (sidethrust)
	[thrstprtcl] (smlEngPrt)
}


[largeEngine]{
	[name]	      (RocketDyne OCS-35/8)
	[tltip]       (heavy booster, great top speed.)
	[cost]        (3)
	[weight]      (2300)
	[turnrate]    (0.15)
	[thrustx]     (0.4)
	[thrusty]     (0.1)
	[strafeRate]  (0.15)
	[img]         (rockDyne)
	[wireframe]	(rockDyneWire)
	[primeThrst]  (test)
	[sideThrst]   (sidethrust)
	[thrstprtcl] (smlEngPrt)
}

[satEngine]{
	[name]	      (herpa derp)
	[tltip]       (heavy booster, great top speed.)
	[cost]        (3)
	[weight]      (2300)
	[turnrate]    (0.4)
	[thrustx]     (0.0)
	[thrusty]     (0.0)
	[strafeRate]  (0.0)
	[img]         (eng1)
	[wireframe]   (thiokWire)
	[primeThrst]  (test)
	[sideThrst]   (sidethrust)
	[thrstprtcl] (smlEngPrt)
}