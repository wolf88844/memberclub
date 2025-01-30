local uniqueKey = KEYS[1]
local uniqueKeyValue = KEYS[2]
local expiredTime = KEYS[3]
local keyCount = KEYS[4]
local kStartIndex = 4;

if (redis.call('set', uniqueKey, uniqueKeyValue, 'NX', 'EX', expiredTime) == false) then
    return -1;
end

for i = 0, keyCount - 1, 1 do
    local k = KEYS[kStartIndex + i * 3 + 1];
    local count = KEYS[kStartIndex + i * 3 + 2];
    local expire = KEYS[kStartIndex + i * 3 + 3];
    redis.call('incrby', k, count);
    redis.call('expire', k, expire);
end

return 1;