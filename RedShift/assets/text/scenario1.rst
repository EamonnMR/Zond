/dummy scenario for testing purposes/
/[scenario1]{/
	[name] (scenario1)	

	[active]{
		[type] (circle)
		[x](0)[y](0)
		[radius](64000)
		/[segments](24)/
	}

	[margin]{
		[type] (circle)
		[x](0)[y](0)
		[radius](96000)
		/[segments](24)/
	}
	
	[spawnX]	(0)
	[spawnY]	(0)

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
				/[segments](24)/
			}
			[target]	(iniSkyLab)
		}
		{
			[type]		(spawn)
			[name]		(iniSkylab)
			[trigtype]	(null)
			[x]		(0)
			[y]		(0)
			[collider]{
				[type] (circle)
				[x](96000)[y](96000)
				[radius](1)
				/[segments](24)/
			}
			[target]	(iniAIShipTest)
			[toSpawn]{
				[kind]	(skylab)
				[gun]	(null)
				[engine](null)
				[loc]	(0)(0)
				[deatheffects](null)
				[isAi]	(false)
			}
		}
		{
			[type]		(spawn)
			[name]		(iniAIShipTest)
			[trigtype]	(SHIP)
			[x]		(0)
			[y]		(256)
			[collider]{
				[type] (circle)
				[x](96000)[y](96000)
				[radius](1)
				/[segments](24)/
			}
			[target]	(null)
			[toSpawn]{
				[kind]	(zond4)
				[gun]	(las)
				[engine](mediumEngine)
				[loc]	(0)(256)
				[deatheffects](null)
				[isAi]	(true)
			}
		}
		/Level Triggers/
		/---Alpha Objective/
		/------nav point trigger/
		{
			[type]		(toggleNav)
			[name]		(NavAlpha)
			[trigtype]	(SHIP)
			[x]		(2584)
			[y]		(2088)
			[collider]{
				[type] (circle)
				[x](2584)[y](2088)
				[radius](64)
				/[segments](24)/
			}
			[target]	(spawnAlphaVosk1)
			[navPointName]	(alpha)
			[initialState]	(true)
		}
		/------spawn voskhod1/
		{
			[type]		(spawn)
			[name]		(spawnAlphaVosk1)
			[trigtype]	(null)
			[x]		(2000)
			[y]		(2088)
			[collider]{
				[type] (circle)
				[x](96000)[y](96000)
				[radius](1)
				/[segments](24)/
			}
			[target]	(spawnAlphaVosk2)
			[toSpawn]{
				[kind]	(voskhod)
				[gun]	(plas)
				[engine](smallEngine)
				[loc]	(2000)(2088)
				[deatheffects](null)
				[isAi]	(true)		
			}
		}
		/------spawn voskhod2/
		{
			[type]		(spawn)
			[name]		(spawnAlphaVosk2)
			[trigtype]	(null)
			[x]		(2584)
			[y]		(1500)
			[collider]{
				[type] (circle)
				[x](96000)[y](96000)
				[radius](1)
				/[segments](24)/
			}
			[target]	(spawnAlphaVstk1)
			[toSpawn]{
				[kind]	(voskhod)
				[gun]	(plas)
				[engine](smallEngine)
				[loc]	(2584)(1500)
				[deatheffects](null)
				[isAi]	(true)			
			}
		}
		/------spawn vostok1/
		{
			[type]		(spawn)
			[name]		(spawnAlphaVstk1)
			[trigtype]	(null)
			[x]		(3000)
			[y]		(2088)
			[collider]{
				[type] (circle)
				[x](96000)[y](96000)
				[radius](1)
				/[segments](24)/
			}
			[target]	(spawnAlphaVosk3)
			[toSpawn]{
				[kind]	(vostok)
				[gun]	(60mm)
				[engine](mediumEngine)
				[loc]	(3000)(2088)
				[deatheffects](null)
				[isAi]	(true)			
			}
		}
		
	>

	/NavPoints/
	[navpoints]{
		[point]{
			[name]		(alpha)
			[x]		(2584)
			[y]		(2088)
			[state]		(true)
		}
	}
/}/