/Scenario File alpha format/

[filename]	(Nato_1)		

[uiname] 	(Dawn Patrol)		/name of the scenario, used as the name in the storage system/	

[faction]	(1)			/USSR or NATO?/

[tltip]		(A short patrol near Jupiter)

[desc]		(We're having problems around the Jupiter Transit. I need you to run a patrol this morning.)

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

[spawnX]	(-5000)
[spawnY]	(-4000)

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
		[x]		(5000)
		[y]		(5000)
		[state]		(t)
	}

	{
		[name]		(alpha)
		[x]		(25000)
		[y]		(35000)
		[state]		(t)
	}
>

/objectives/
[objectives]<
	{
		[name]	(patrolAlpha)
		[tltip]	(Patrol Nav Alpha)
		[desc]	(Enemy activity reported at Nav Alpha, at least two Voskhod class ships.)
		[target](allObjectives)
		[state]	(false)	
	}
	{
		[name]	(sweepBeta)
		[tltip]	(Remove 5 asteroids from Nav Beta)
		[desc]	(We're installing a new listening post at Nav Beta, we need you to clear asteroids around Beta. Careful large asteroids will damage your hull.)
		[target]()
		[state]	(false)
	}
	{
		[name]	(patrolAlpha)
		[tltip]	(Patrol Nav Alpha)
		[desc]	(Enemy activity reported at Nav Alpha, at least two Voskhod class ships.)
		[target](allObjectives)
		[state]	(false)	
	}
>

/triggers/
[triggers]<
	/-win the level-/
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
	/-Objective Counter-/
	{
		[type]		(count)
		[name]		(allObjectives)
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
		[total]		(3)
	}
	/-Alpha complete objective -/
	{
		[type]		(endobj)
		[name]		(finishAlpha)
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
		[objective]	(patrolAlpha)
	}
	/-Alpha killcounter-/
	{
		[type]		(count)
		[name]		(patrolAlpha)
		[trigtype]	(TRIGGER)
		[x]		(96000)
		[y]		(96000)
		[collider]{
			[type] (circle)
			[x](96000)[y](96000)
			[radius](1)
		}
		[target]	(navalphaoff)
		[trigstate]	(f)
		[total]		(3)
	}
	/-shutoff nav alpha-/
	{
		[type]		(togglenav)
		[name]		(navalphaoff)
		[trigtype]	(TRIGGER)
		[x]		(96000)
		[y]		(96000)
		[collider]{
			[type] (circle)
			[x](96000)[y](96000)
			[radius](1)
		}
		[target]	(finishAlpha)
		[trigstate]	(f)
		[navPointName]	(alpha)
		[setstate]	(f)
	}
	/-Alpha ambush!-/
	{
		[type]		(multrig)
		[name]		(multi)
		[trigtype]	(SHIP)
		[x]		(96000)
		[y]		(96000)
		[collider]{
			[type] (circle)
			[x](5000)[y](5000)
			[radius](64)
		}
		[target]	()
		[trigstate]	(f)
		[targets]{
			[targ0]	(spawnAlphaVosk2)
			[targ1]	(spawnAlphaVstk1)
			[targ2]	(spawnAlphaVskh1)
		}
	}
	/------spawn voskhod2/
	{
		[type]		(spawn)
		[name]		(spawnAlphaVosk2)
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
			[kind]	(voskhod)
			[gun]	(plas)
			[engine](smallEngine)
			[loc]	(5400 5400)
			[isAi]	(f)	
			[deathtrig](patrolAlpha)
		}
	}
	/------spawn vostok1/
	{
		[type]		(spawn)
		[name]		(spawnAlphaVstk1)
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
			[kind]	(vostok)
			[gun]	(60mm)
			[engine](medEngine)
			[loc]	(5800 4600)
			[isAi]	(f)
			[deathtrig](patrolAlpha)
		}
	}
	/------spawn vostok1/
	{
		[type]		(spawn)
		[name]		(spawnAlphaVskh1)
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
			[kind]	(voskhod)
			[gun]	(las)
			[engine](smallEngine)
			[loc]	(5900 5100)
			[isAi]	(f)
			[deathtrig](patrolAlpha)
		}
	}

>