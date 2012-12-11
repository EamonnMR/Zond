/Scenario File alpha format/

[filename]	(scenario1)		

[uiname] 	(scenario1)		/name of the scenario, used as the name in the storage system/	

[faction]	(1)			/USSR or NATO?/

[tltip]		(A short test scnenario)

[desc]		(We're having problems around the Neptune Transit. I need you to run a patrol this morning.)

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

[music]	(name)

[spawnX]	(0)
[spawnY]	(0)

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
	
/NavPoints/
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

/objectives/
[objectives]<
	{
		[name]	(PatrolAlpha)
		[tltip]	(Patrol Nav Alpha)
		[desc]	(Enemy activity reported at Nav Alpha, at least two Voskhod class ships.)
		[target]()
		[state]	(false)	
	}
	{
		[name]	(SweepBeta)
		[tltip]	(Remove 5 asteroids from Nav Beta)
		[desc]	(We're installing a new listening post at Nav Beta, we need you to clear asteroids around Beta. Careful large asteroids will damage your hull.)
		[target]()
		[state]	(false)
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
		/[trigstate]	(true-false)/
			
		/for spawn type/
		/[toSpawn]{SHIPDESC}/
			
		/for togglenav type/
		/[navPointName]	(NAME)/
		/[setstate]	(t-f)/
		
		/for count type/
		/[total]	(number)/
	
		/for multrig type/
		/[target]	(string) for every trig name/
			
	/}/
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
			[engine]()
			[loc]	(768 0)
			[deatheffects](null)
			[isAi]	(f)
			[deathtrig]()
		}
	}
	{
		[type]		(spawn)
		[name]		(iniAIShipTest)
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
		[toSpawn]{
			[kind]	(zond4)
			[gun]	(las)
			[engine](medEngine)
			[loc]	(256 256)
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
		[target]	(spawnAlphaVstk1)
		[trigstate]	(f)
		[toSpawn]{
			[kind]	(voskhod)
			[gun]	(plas)
			[engine](smallEngine)
			[loc]	(2584 1500)
			[deatheffects](null)
			[isAi]	(f)	
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
			[engine](medEngine)
			[loc]	(3000 2088)
			[deatheffects](null)
			[isAi]	(f)
			[deathtrig](killatalpha)			
		}
	}	
>