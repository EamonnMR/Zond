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
	[cooldown](200)
	[cost]    (1)
	[img]     (gun1)
	[weight]  (119)
	[proj]    (twentyShot)
	[fireSnd] (twentys)
}

/60 mm cannon/
[60mm]{
	[cooldown](300);
	[cost]    (2)
	[img]     (gun2)
	[weight]  (540)
	[proj]    (sixtyShot)
	[fireSnd] (twentys)
}

/105 mm cannon/
[105mm]{
	[cooldown](400);
	[cost]    (3)
	[img]     (gun3)
	[weight]  (983)
	[proj]    (oneFiveShot)
	[fireSnd] (twentys)
}

/small plasma/
[smallPlas]{
	[cooldown](150);
	[cost]    (0)
	[img]     (gun1) / Why aren't we loading the images? /
	[weight]  (0)
	[proj]    (oneFiveShot) /No projectile type yet/
	[fireSnd] (twentys)
}

/small laser/
[smallLaser]{
	[cooldown](150);
	[cost]    (0)
	[img]     (gun1)
	[weight]  (984)
	[proj]    (oneFiveShot) /No projectile type yet/
	[fireSnd] (twentys)
}