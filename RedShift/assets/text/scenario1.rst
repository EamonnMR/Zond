/dummy scenario for testing purposes/
[scenario1]{
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
		{
			[type]		(?)
			[name]		(NAME)
			[trigtype]	(TYPE)
			[x]		(0)
			[y]		(0)
			[collider]{SHAPE}
			[target]	(trg1)
			/Only if it's a "spawn" trigger/
			[toSpawn]{SHIPDESC}
			/Only if it's a togglenav/
			[navPointName](NAME)
			[initialState](INITIAL STATE)
			
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
}