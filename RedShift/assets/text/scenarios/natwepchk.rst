/Scenario File alpha format/

[filename]	(natwpchk)

[uiname]	(Nato Weapons Check)

[faction]	(1)

[tltip]		(test nato weapons and equipment)

[desc]		(Access granted, I'll open the rare stock. Enjoy, Commander.
Feel free to test every ship, engine, weapon to get a feel for each.
You will be provided with some targets to test things on.)

/scenario-specific things/
[levelType]	(scen)
	
/gun descriptors here/	
[plrGuns]{
	[item0]	(20mm)
	[item1]	(60mm)
	[item2]	(las)
}

/motor descriptors here/
[plrMotors]{
	[item0]	(smallEngine)
	[item1]	(medEngine)
	[item2]	(largeEngine)
}

/ship descriptors here/
[plrShips]{
	[item0]	(mercury)
	[item1]	(gemini)
	[item2]	(lunar)
}

[music]	(loneRecon)

[spawnX]	(0)
[spawnY]	(0)

[active]{
	[type] (circle)
	[x](0)[y](0)
	[radius](10000)
}

[margin]{
	[type] (circle)
	[x](0)[y](0)
	[radius](10000)
}

[navpoints]<
	{
		[name]	(alpha)
		[x]	(0)
		[y]	(0)
		[state]	(t)
	}
>

[objectives]<
	{
		[name]	(TestWeps)
		[tltip]	(test Nato equipment)
		[desc]	(You will be given access to all Nato items.
Mission ends when all targets are destroyed.)
		[target]()
		[state]	(false)	
	}
>

[triggers]<
	/trigger the target spawning/
	{
		[type]	(multrig)
		[name]	(fireini)
		[trigtype](SHIP)
		[x]	(0)
		[y]	(0)
		[collider]{
			[type] (circle)
			[x](0)[y](0)
			[radius](64)		
		}
		[target]()
		[trigstate](f)
		[targets]{
			[targ0](spawnVoskhd)
			[targ1](spawnVostk)
			[targ2](spawnZond)
			[targ3](spawnSpud)
			[targ4](spawnMars3)
		}	
	}

	/---Win the level/
	{
		[type]		(iwin)
		[name]		(thewin)
		[trigtype]	(TRIGGER)
		[x]		(96000)
		[y]		(96000)
		[collider]{
			[type] (circle)
			[x](96000)[y](96000)
			[radius](1)
		}
		[target]	()
		[trigstate]	(f)
		[state]		(1)
	}
	/------Counter/
	{
		[type]		(count)
		[name]		(targetscleared)
		[trigtype]	(TRIGGER)
		[x]		(96000)
		[y]		(96000)
		[collider]{
			[type] (circle)
			[x](96000)[y](96000)
			[radius](1)
		}
		[target]	(thewin)
		[trigstate]	(f)
		[total]		(5)
	}

	/make a vostok/
	{
		[type]		(spawn)
		[name]		(spawnVoskhd)
		[trigtype]	(TRIGGER)
		[x]		(-400)
		[y]		(200)
		[collider]{
			[type] (circle)
			[x](96000)[y](96000)
			[radius](1)
		}
		[target]	()
		[trigstate]	(f)
		[toSpawn]{
			[kind]	(voskhod)
			[gun]	(plas)
			[engine](smallEngine)
			[loc]	(-400 200)
			[isAi]	(f)	
			[deathtrig](targetscleared)
		}
	}
	/make a voskhod/
	{
		[type]		(spawn)
		[name]		(spawnVostk)
		[trigtype]	(TRIGGER)
		[x]		(-300)
		[y]		(200)
		[collider]{
			[type] (circle)
			[x](96000)[y](96000)
			[radius](1)
		}
		[target]	()
		[trigstate]	(f)
		[toSpawn]{
			[kind]	(vostok)
			[gun]	(plas)
			[engine](smallEngine)
			[loc]	(-300 200)
			[isAi]	(f)	
			[deathtrig](targetscleared)
		}
	}
	/make a zond/
	{
		[type]		(spawn)
		[name]		(spawnZond)
		[trigtype]	(TRIGGER)
		[x]		(-200)
		[y]		(200)
		[collider]{
			[type] (circle)
			[x](96000)[y](96000)
			[radius](1)
		}
		[target]	()
		[trigstate]	(f)
		[toSpawn]{
			[kind]	(zond4)
			[gun]	(plas)
			[engine](smallEngine)
			[loc]	(-200 200)
			[isAi]	(f)	
			[deathtrig](targetscleared)
		}
	}
	/make a spud/
	{
		[type]		(spawn)
		[name]		(spawnSpud)
		[trigtype]	(TRIGGER)
		[x]		(-100)
		[y]		(200)
		[collider]{
			[type] (circle)
			[x](96000)[y](96000)
			[radius](1)
		}
		[target]	()
		[trigstate]	(f)
		[toSpawn]{
			[kind]	(sputnik3)
			[gun]	()
			[engine](satEngine)
			[loc]	(-100 200)
			[isAi]	(f)	
			[deathtrig](targetscleared)
		}
	}
	}
	/make a mars3/
	{
		[type]		(spawn)
		[name]		(spawnMars3)
		[trigtype]	(TRIGGER)
		[x]		(0)
		[y]		(200)
		[collider]{
			[type] (circle)
			[x](96000)[y](96000)
			[radius](1)
		}
		[target]	()
		[trigstate]	(f)
		[toSpawn]{
			[kind]	(mars3)
			[gun]	(plas)
			[engine](satEngine)
			[loc]	(0 200)
			[isAi]	(f)	
			[deathtrig](targetscleared)
		}
	}
>