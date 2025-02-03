local key = KEYS[1]
local versionKey = KEYS[2]
local version = KEYS[3]
local valueKey = KEYS[4]
local value = KEYS[5]

if (redis.call('hexists', key, versionKey) == 0) then
    redis.call('hmset', key, versionKey, version, valueKey, value)
    return 1;
else
    local oldVersion = redis.call('hget', key, versionKey)
    if (tonumber(version) > tonumber(oldVersion)) then
        redis.call('hmset', key, versionKey, version, valueKey, value)
        return 2
    else
        return -1;
    end
end