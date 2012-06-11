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
	[cost]    (0)
	[img]     (gun1)
	[weight]  (0)
	[proj]    (twentyShot)
}

/60 mm cannon/
[60mm]{
	[cooldown](300);
	[cost]    (0)
	[img]     (gun2)
	[weight]  (0)
	[proj]    (sixtyShot)
}

/105 mm cannon/
[105mm]{
	[cooldown](400);
	[cost]    (0)
	[img]     (gun3)
	[weight]  (0)
	[proj]    (oneFiveShot)
}

/small plasma/
[smallPlas]{
	[cooldown](150);
	[cost]    (0)
	[img]     (gun1) / Why aren't we loading the images? /
	[weight]  (0)
	[proj]    (oneFiveShot) /No projectile type yet/
}

/small laser/
[smallLaser]{
	[cooldown](150);
	[cost]    (0)
	[img]     (gun1)
	[weight]  (0)
	[proj]    (oneFiveShot) /No projectile type yet/
}