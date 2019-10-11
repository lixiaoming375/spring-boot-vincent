local times =redis.call('incr',KEYS[1])

if( tonumber(times)==1) then
    redis.call('expire',KEYS[1],ARVG[1])
    return 1
elseif  tonumber(times)>tonumber(ARVG[2])
    return 0
else
    return 1
end


