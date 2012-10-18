 /LevelDataModel RST/

[name]{ /Name of the level/
    /Lists/
    [triggers]{
	[name]{/Name of the level
            [target](NAME) /Name of the action it fires off?/
            [loc](POINT) /Location of the trigger.  DO we need this if it's a collider anyway?/
            [type](TYPE) /What trigger type it is

            /Optional values:/
            [shape]{COLLIDER} /Collider tree for the object/
            [total](INT) /Will make it a count trigger; total that it must reach/
            [class](DEATH | MULTI | ETC)/Use special trigger class... aren't those actions?
            do these need to exist?/
            /These depend on the flags.
            [multi]<
                (NAME) /Triggers to execute./
                ...
            >
            [spawnship](NAME) /Name of the ship to spawn.  This overrides any other optional value./
        }
            
    [actions]{
        [name]{
            [class](MESSAGE | etc...)
            /Optional:/
            [message](STRING)
            [loc](POINT)
        }
    [navpoints]{
        /[name]{
            [name](STRING)
            [loc](POINT)
            [state](BOOLEAN)
        }/
        [navAlpha]{
            [words](Alpha)
            [loc]  (2584 2088)
            [state] (1)
        }
        [navBeta]{
            [words](Beta)
            [loc]  (-2336 5670)
            [state](1)
        }
        [navCappa]{
            [words](Cappa)
            [loc]  (-2816 -2592)
            [state](1)
        [navDelta]{
            [words](Delta)
            [loc]  (1928 -2104)
            [state](1)
        [navEpsilon]
            [words](Epsilon)
            [loc]  (0 0)
            [state](1)
    }
    [objectives]{
        /[name]{
            [words](STRING)
            [checked](BOOLEAN)
            [visible](BOOLEAN)
        }/
    }
    /Might we also want a list of ships to start with?/


    /Specifics/
    [activeArea]{
        [x] (-4800) [y] (-4800) [w] (9600) [h](9600)
    }
    [warnArea]{
        [x] (-3200) [y] (-3200) [w] (6400) [h](6400)
    }

    /Unimplemented, but LevelDataModel does not implement them ether./

    [spawn]      (0 0) /Where the player spawns/
    [music]      (NAME) /Sound to use for music/
    [bgimg]      (level1field) /Image to use as the background./

    /BuildLevel never uses getShips, but hell, why not?/
    [ships]<
        {[kind](NAME) [loc](POINT)}
    >



}