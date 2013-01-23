/Scenario File alpha format/

[filename]	(sovwpchk)

[uiname]	(Soviet Weapons Check)

[faction]	(0)

[tltip]		(test soviet weapons and equipment)

[desc]		(Access granted, I'll open the rare stock. Enjoy, Commander.
You will be provided with some targets to test things on.)

/scenario-specific things/
[levelType]	(scen)
	
[plrGuns]{
	[item0]	(plas)
        [item1] (105mm)
	[item2]	(mcrwv)
}
[plrMotors]{
	[item0]	(rd109)
	[item1] (ang191)
	[item2] (7kl3)
}
[plrShips]{
	[item0]	(zond4)
	[item1]	(voskhod)
	[item2]	(vostok)
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
		[tltip]	(test Soviet equipment)
		[desc]	(You will be given access to all Soviet items.
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
			[targ0](spawnMerc)
			[targ1](spawnGem)
			[targ2](spawnLoon)
			[targ3](spawnPionrS)
			[targ4](spawnPionrG)
			[targ5](spawnVoyag)
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
		[total]		(6)
	}

	/make a mercury/
	{
		[type]		(spawn)
		[name]		(spawnMerc)
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
			[kind]	(mercury)
			[gun]	(60mm)
			[engine](smallEngine)
			[loc]	(-100 200)
			[isAi]	(f)	
			[deathtrig](targetscleared)
		}
	}
	/make a gemini/
	{
		[type]		(spawn)
		[name]		(spawnGem)
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
			[kind]	(gemini)
			[gun]	(60mm)
			[engine](medEngine)
			[loc]	(0 200)
			[isAi]	(f)	
			[deathtrig](targetscleared)
		}
	}
	/make a lunar/
	{
		[type]		(spawn)
		[name]		(spawnLoon)
		[trigtype]	(TRIGGER)
		[x]		(400)
		[y]		(200)
		[collider]{
			[type] (circle)
			[x](96000)[y](96000)
			[radius](1)
		}
		[target]	()
		[trigstate]	(f)
		[toSpawn]{
			[kind]	(lunar)
			[gun]	(105mm)
			[engine](largeEngine)
			[loc]	(100 200)
			[isAi]	(f)	
			[deathtrig](targetscleared)
		}
	}

	/make a pioneer sat/
	{
		[type]		(spawn)
		[name]		(spawnPionrS)
		[trigtype]	(TRIGGER)
		[x]		(400)
		[y]		(200)
		[collider]{
			[type] (circle)
			[x](96000)[y](96000)
			[radius](1)
		}
		[target]	()
		[trigstate]	(f)
		[toSpawn]{
			[kind]	(pionrSat)
			[gun]	()
			[engine](satEngine)
			[loc]	(200 200)
			[isAi]	(f)	
			[deathtrig](targetscleared)
		}
	}


	/make a pioneer sat wiv gunz!/
	{
		[type]		(spawn)
		[name]		(spawnPionrG)
		[trigtype]	(TRIGGER)
		[x]		(400)
		[y]		(200)
		[collider]{
			[type] (circle)
			[x](96000)[y](96000)
			[radius](1)
		}
		[target]	()
		[trigstate]	(f)
		[toSpawn]{
			[kind]	(pionrGun)
			[gun]	(las)
			[engine](satEngine)
			[loc]	(300 200)
			[isAi]	(f)	
			[deathtrig](targetscleared)
		}
	}

	/make a voyager sat wiv gunz!/
	{
		[type]		(spawn)
		[name]		(spawnVoyag)
		[trigtype]	(TRIGGER)
		[x]		(400)
		[y]		(200)
		[collider]{
			[type] (circle)
			[x](96000)[y](96000)
			[radius](1)
		}
		[target]	()
		[trigstate]	(f)
		[toSpawn]{
			[kind]	(voyagSat)
			[gun]	(las)
			[engine](satEngine)
			[loc]	(400 200)
			[isAi]	(f)	
			[deathtrig](targetscleared)
		}
	}


>