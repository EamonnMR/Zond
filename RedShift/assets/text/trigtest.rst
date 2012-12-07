/Scenario File alpha format/

[name] (multitest)	

[faction]	(0)

[tltip]		(testing multitrigger function)

[desc]		(Patrol each way point, kill things)

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

[spawnX]	(50)
[spawnY]	(50)

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

/NavPoints must be before trigs :P/
/waitta minute...buildNavPointTrigger creates a new nav point..../
[navpoints]<
	{
		[name]		(alpha)
		[x]		(0)
		[y]		(0)
		[state]		(t)
	}
>

/triggerzzzzzzz/
[triggers]<
	{
		[type]		(multrig)
		[name]		(multi)
		[trigtype]	(SHIP)
		[x]		(0)
		[y]		(0)
		[collider]{
			[type] (circle)
			[x](0)[y](0)
			[radius](10)
		}
		[target]	()
		[trigstate]	(f)
		[targets]{
			[targ0]	(spawnAlphaVosk2)
			[targ1]	(spawnAlphaVstk1)
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
			[engine](smallEngine)
			[loc]	(200 -200)
			[deatheffects](null)
			[isAi]	(f)	
			[deathtrig]()
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
			[loc]	(-200 200)
			[deatheffects](null)
			[isAi]	(f)
			[deathtrig]()
		}
	}
>