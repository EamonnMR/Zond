/Shot types
[name]{
	[img]   (STRING)  Image
	[dmg]   (INT)     Damage
	[speed] (FLOAT)Speed
	[life]  (INT) Lifetime (miliseconds?)
	[snd]   (STRING) name of the sound to use when the shot hits
	[collider] SHAPE (see shapes.txt)
}/

[twentyShot]{
	[img]  (shot1)
	[dmg]  (5)
	[speed](0.4)
	[life] (10000)
	[snd]  (twentyh)	
	[collider]{
		[type](circle)
		[x](0)[y](0)
		[radius](4)
	}
}


[sixtyShot]{
	[img]  (shot2)
	[dmg]  (5)
	[speed](0.4)
	[life] (7500)
	[snd]  (twentyh)
	[collider]{
		[type](circle)
		[x](0)[y](0)
		[radius](4)
	}
}


[oneFiveShot]{
	[img]  (shot3)
	[dmg]  (15)
	[speed](0.4)
	[life] (5000)
	[snd]  (twentyh)
	[collider]{
		[type](circle)
		[x](0)[y](0)
		[radius](4)
	}
}

[plasShot]{
	[img]  (plas)
	[dmg]  (5)
	[speed](0.5)
	[life] (5000)
	[snd]  (twentyh)
	[collider]{
		[type](circle)
		[x](0)[y](0)
		[radius](4)
	}
}

[lazShot]{
	[img]  (laz)
	[dmg]  (1)
	[speed](1.0)
	[life] (325)
	[snd]  (smlHt)
	[collider]{
		[type](circle)
		[x](0)[y](0)
		[radius](4)
	}
}