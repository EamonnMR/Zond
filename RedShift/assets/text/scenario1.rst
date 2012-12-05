/dummy scenario for testing purposes/

[name] (scenario1)	

[faction]	(1)

[active]{
	[type] (circle)
	[x](0)[y](0)
	[radius](64000)
}

[margin]{
	[type] (circle)
	[x](0)[y](0)
	[radius](96000)
}
	
[spawnX]	(512)
[spawnY]	(250)

[music]	(name)
	
/scenario-specific things/
[levelType]	(scen) /as opposed to camp for campaign/
	
[plrGuns]{
	/gun descriptors here/
}
[plrMotors]{
	/motor descriptors here/
}
[plrShips]{
	/ship descriptors here/
}

/NavPoints must be before trigs :P/
/waitta minute...buildNavPointTrigger creates a new nav point..../
[navpoints]<
	{
		[name]		(alpha)
		[x]		(2584)
		[y]		(2088)
		[state]		(t)
	}
	{
		[name]		(beta)
		[x]		(-2336)
		[y]		(5670)
		[state]		(t)
	}
	{
		[name]		(cappa)
		[x]		(-2816)
		[y]		(-2104)
		[state]		(t)
	}
	{
		[name]		(delta)
		[x]		(1928)
		[y]		(-2104)
		[state]		(t)
	}
	{
		[name]		(epsilon)
		[x]		(0)
		[y]		(0)
		[state]		(t)
	}
>

/Triggers/
[triggers]<
	/{/
		/[type]		(?)/
		/[name]		(NAME)/
		/[trigtype]	(TYPE)/
		/[x]		(0)/
		/[y]		(0)/
		/[collider]{SHAPE}/
		/[target]	(trg1)/
		/[trigstate]	()/
			
		/Only if it's a "spawn" trigger/
		/[toSpawn]{SHIPDESC}/
			
		/Only if it's a togglenav/
		/[navPointName](NAME)/
		/[initialState](INITIAL STATE)/
			
	/}/
	/INI triggers - fired at level start/
	{
		[type]		(ini)
		[name]		(fireINI)
		[trigtype]	(SHIP)
		[x]		(512)
		[y]		(396)
		[collider]{
			[type] (circle)
			[x](512)[y](396)
			[radius](1024)
		}
		[target]	(iniskylab)
		[trigstate]	(f)
	}
	{
		[type]		(spawn)
		[name]		(iniskylab)
		[trigtype]	(TRIGGER)
		[x]		(0)
		[y]		(0)
		[collider]{
			[type] (circle)
			[x](96000)[y](96000)
			[radius](1)
		}
		[target]	(iniAIShipTest)
		[trigstate]	(f)
		[toSpawn]{
			[kind]	(skylab)
			[gun]	()
			[engine]()
			[loc]	(0 0)
			[deatheffects](null)
			[isAi]	(f)
		}
	}
	{
		[type]		(spawn)
		[name]		(iniAIShipTest)
		[trigtype]	(TRIGGER)
		[x]		(0)
		[y]		(256)
		[collider]{
			[type] (circle)
			[x](96000)[y](96000)
			[radius](1)
		}
		[target]	()
		[trigstate]	(f)
		[toSpawn]{
			[kind]	(zond4)
			[gun]	(las)
			[engine](medEngine)
			[loc]	(0 256)
			[deatheffects](null)
			[isAi]	(f)
		}
	}
	/Level Triggers/
	/---Alpha Objective/
	/------Alpha Counter
	{
		[type]		(count)
		[name]		(killatalpha)
		[trigtype]	(TRIGGER)
		[x]		(0)
		[y]		(0)
		[collider]{
			[type] (circle)
			[x](0)[y](0)
			[radius](1)
		}
		[target]	(alphaoff)
		[trigstate]	(f)
		[total]		(3)
	}
	/------shut off alpha
	{
		[type]		(togglenav)
		[name]		(alphaoff)
		[trigtype]	(TRIGGER)
		[x]		(0)
		[y]		(0)
		[collider]{
			[type] (circle)
			[x](0)[y](0)
			[radius](1)
		}
		[target]	()
		[trigstate]	(f)
		[navPointName]	(alpha)
		[setstate]	(false)
	}
	/------nav point trigger/
	{
		[type]		(togglenav)
		[name]		(NavAlpha)
		[trigtype]	(SHIP)
		[x]		(2584)
		[y]		(2088)
		[collider]{
			[type] (circle)
			[x](2584)[y](2088)
			[radius](64)
		}
		[target]	(spawnAlphaVosk1)
		[trigstate]	(f)
		[navPointName]	(alpha)
		[setstate]	(true)
	}
	/------spawn voskhod1/
	{
		[type]		(spawn)
		[name]		(spawnAlphaVosk1)
		[trigtype]	(TRIGGER)
		[x]		(2000)
		[y]		(2088)
		[collider]{
			[type] (circle)
			[x](96000)[y](96000)
			[radius](1)
		}
		[target]	(spawnAlphaVosk2)
		[trigstate]	(f)
		[toSpawn]{
			[kind]	(voskhod)
			[gun]	(plas)
			[engine](smallEngine)
			[loc]	(2000 2088)
			[deatheffects](null)
			[isAi]	(f)		
		}
	}
	/-------voskhod1 dies/

	/------spawn voskhod2/
	{
		[type]		(spawn)
		[name]		(spawnAlphaVosk2)
		[trigtype]	(TRIGGER)
		[x]		(2584)
		[y]		(1500)
		[collider]{
			[type] (circle)
			[x](96000)[y](96000)
			[radius](1)
		}
		[target]	(spawnAlphaVstk1)
		[trigstate]	(f)
		[toSpawn]{
			[kind]	(voskhod)
			[gun]	(plas)
			[engine](smallEngine)
			[loc]	(2584 1500)
			[deatheffects](null)
			[isAi]	(f)			
		}
	}
	/-------voskhod2 dies/
	/------spawn vostok1/
	{
		[type]		(spawn)
		[name]		(spawnAlphaVstk1)
		[trigtype]	(TRIGGER)
		[x]		(3000)
		[y]		(2088)
		[collider]{
			[type] (circle)
			[x](96000)[y](96000)
			[radius](1)
		}
		[target]	()
		[trigstate]	(f)
		[toSpawn]{
			[kind]	(vostok)
			[gun]	(60mm)
			[engine](medEngine)
			[loc]	(3000 2088)
			[deatheffects](null)
			[isAi]	(f)			
		}
	}
	/-------vostok1 dies/		
>