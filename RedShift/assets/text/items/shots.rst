/Shot types
[name]{
	[img]   (STRING)  Image
	[dmg]   (INT)     Damage
	[speed] (FLOAT)Speed
	[life]  (INT) Lifetime (miliseconds?)
	[snd]   (STRING) name of the sound to use when the shot hits
	[collider] SHAPE (see shapes.txt)
	[prtcl] (STRING) the pointer to the particle in the system
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
	[prtcl] (20HitPrt)
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
	[prtcl] (60HitPrt)
}


[oneFiveShot]{
	[img]  (shot3)
	[dmg]  (30)	/(15)/
	[speed](0.4)
	[life] (5000)
	[snd]  (twentyh)
	[collider]{
		[type](circle)
		[x](0)[y](0)
		[radius](4)
	}
	[prtcl] (105HitPrt)
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
	[prtcl] (plasHitPrt)
}

[lazShot]{
	[img]  (laz)
	[dmg]  (1)
	[speed](1.4)
	[life] (325)
	[snd]  (smlHt)
	[collider]{
		[type](circle)
		[x](0)[y](0)
		[radius](4)
	}
	[prtcl] (lazHitPrt)
}

[mcrwvShot]{
	[img]  (mcrwvfx)
	[dmg]  (3)
	[speed](1.4)
	[life] (225)
	[snd]  (smlHt)
	[collider]{
		[type](circle)
		[x](0)[y](0)
		[radius](18)
	}
	[prtcl] (mcrHitPrt)
}