/Shot types
[name]{
	[img]   (STRING)  Image
	[dmg]   (INT)     Damage
	[speed] (FLOAT)Speed
	[life]  (INT) Lifetime (miliseconds?)
	[collider] SHAPE (see shapes.txt)
}/

[twentyShot]{
	[img]  (shot1)
	[dmg]  (5)
	[speed](0.15)
	[life] (10000)
	[collider]{
		[type](circle)
		[x](0)[y](0)
		[radius](4)
	}
}


[sixtyShot]{
	[img]  (shot1)
	[dmg]  (5)
	[speed](0.12)
	[life] (7500)
	[collider]{
		[type](circle)
		[x](0)[y](0)
		[radius](4)
	}
}


[oneFiveShot]{
	[img]  (shot1)
	[dmg]  (5)
	[speed](0.1)
	[life] (5000)
	[collider]{
		[type](circle)
		[x](0)[y](0)
		[radius](4)
	}
}