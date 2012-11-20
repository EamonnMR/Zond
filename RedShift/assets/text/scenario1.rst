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
	[triggers]{
		[trig]{
			[type]		(ini)
			[name]		(fireIni)
			[trigtype]	(ALL)
			[x]		(0)
			[y]		(0)
			[target]	(spawn1)
		}
		[trig]{
			[type]		(spawn)
			[name]		(spawn1)
			[trigtype]	(ALL)
			[x]		(0)
			[y]		(0)
			[target]	(trg1)
			[shipDesc]{
				/ship to spawn/
			}
		}


	}

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