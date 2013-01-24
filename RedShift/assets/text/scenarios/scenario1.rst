/Scenario File alpha format/

[filename]	(scenario1)		

[uiname] 	(Nato 1)		/name of the scenario, used as the name in the storage system/	

[faction]	(1)			/USSR or NATO?/

[tltip]		(Simple Patrol)

[desc]		(We're having problems around the Neptune Transit.
 I need you to run a patrol this morning.)

/scenario-specific things/
[levelType]	(scen) /as opposed to camp for campaign/

/gun descriptors here/	
[plrGuns]{
	[item0]	(20mm)
	[item1]	(60mm)
	[item2] (las)
}

/motor descriptors here/
[plrMotors]{
	[item0]	(medEngine)
}

/ship descriptors here/
[plrShips]{
	[item0]	(gemini)
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
	
/NavPoints/
[navpoints]<
	{
		[name]		(alpha)
		[x]		(2584)
		[y]		(2088)
		[state]		(t)
	}
	{
		[name]		(epsilon)
		[x]		(0)
		[y]		(0)
		[state]		(t)
	}
>

/objectives/
[objectives]<
	{
		[name]	(PatrolAlpha)
		[tltip]	(Patrol Nav Alpha)
		[desc]	(Enemy activity reported at Nav Alpha, at least two Voskhod class ships.)
		[target]()
		[state]	(true)	
	}
>

/Triggers/
[triggers]<
	/INI triggers - fired at level start/
	{
		[type]		(ini)
		[name]		(fireINI)
		[trigtype]	(SHIP)
		[x]		(0)
		[y]		(0)
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
		[x]		(96000)
		[y]		(96000)
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
			[engine](satEngine)
			[loc]	(0 -100)
			[deatheffects](null)
			[isAi]	(f)
			[deathtrig]()
		}
	}
	/Level Triggers/
	/---Alpha Objective/
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
	/------Alpha Counter/
	{
		[type]		(count)
		[name]		(killatalpha)
		[trigtype]	(TRIGGER)
		[x]		(96000)
		[y]		(96000)
		[collider]{
			[type] (circle)
			[x](96000)[y](96000)
			[radius](1)
		}
		[target]	(alphaoff)
		[trigstate]	(f)
		[total]		(3)
	}
	/------shut off alpha/
	{
		[type]		(togglenav)
		[name]		(alphaoff)
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
		[navPointName]	(alpha)
		[setstate]	(false)
	}
	/------nav point trigger---ambush!/
	{
		[type]		(multrig)
		[name]		(NavAlpha)
		[trigtype]	(SHIP)
		[x]		(2584)
		[y]		(2088)
		[collider]{
			[type] (circle)
			[x](2584)[y](2088)
			[radius](128)
		}
		[target]	()
		[trigstate]	(f)
		[targets]{
			[targ0](spawnAlphaVosk1)
			[targ1](spawnAlphaVosk2)
			[targ2](spawnAlphaVstk1)
		}
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
		[target]	()
		[trigstate]	(f)
		[toSpawn]{
			[kind]	(voskhod)
			[gun]	(plas)
			[engine](rd109)
			[loc]	(2000 2088)
			[isAi]	(t)
			[deathtrig](killatalpha)
		}
	}
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
		[target]	()
		[trigstate]	(f)
		[toSpawn]{
			[kind]	(voskhod)
			[gun]	(plas)
			[engine](rd109)
			[loc]	(2584 1500)
			[isAi]	(t)	
			[deathtrig](killatalpha)		
		}
	}
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
			[engine](rd109)
			[loc]	(3000 2088)
			[isAi]	(t)
			[deathtrig](killatalpha)			
		}
	}	
>