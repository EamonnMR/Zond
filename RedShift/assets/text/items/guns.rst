/GUN DEFINITIONS:
	[NAME]{
		[cooldown](INT)     Delay between shots
		[cost]    (INT)     Price of the gun
		[img]     (STRING)  Image to use
		[weight]  (INT)     Weight of the gun
		[proj]    (STRING)  Type of projectile to use
	}
/


/20 mm cannon/
[20mm]{
	[name]	  (20mm Chaingun)
	[tltip]   (low damage, rapid-fire, short range gun)
	[cooldown](125)
	[cost]    (1)
	[img]     (gun1)
	[weight]  (119)
	[proj]    (twentyShot)
	[fireSnd] (twentys)
	[wire]    (20wire)
}

/60 mm cannon/
[60mm]{
	[name]	  (60mm Rifle)
	[tltip]   (medium range, average rate cannon)
	[cooldown](150);
	[cost]    (2)
	[img]     (gun2)
	[weight]  (540)
	[proj]    (sixtyShot)
	[fireSnd] (twentys)
	[wire]    (60wire)
}

/105 mm cannon/
[105mm]{
	[name]	  (105mm Cannon)
	[tltip]   (large/high damage cannon)
	[cooldown](300); /(175)/
	[cost]    (3)
	[img]     (gun3)
	[weight]  (983)
	[proj]    (oneFiveShot)
	[fireSnd] (oneohfive)
	[wire]	  (105wire)
}

/small plasma/
[plas]{
	[name]	  (Plasma Rifle)
	[tltip]   (quick fire, medium range energy emitter.)
	[cooldown](100);
	[cost]    (0)
	[img]     (gun1) / Why aren't we loading the images? /
	[weight]  (0)
	[proj]    (plasShot) /No projectile type yet/
	[fireSnd] (zap)
	[wire]	  (105wire)
}

/small laser/
[las]{
	[name]	  (Laser)
	[tltip]   (short range beam of light.)
	[cooldown](25);
	[cost]    (0)
	[img]     (gun1)
	[weight]  (984)
	[proj]    (lazShot) /No projectile type yet/
	[fireSnd] (smlFre)
	[wire]	  (105wire)
}