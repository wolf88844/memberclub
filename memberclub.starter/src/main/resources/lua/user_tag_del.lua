local uniqueKey = KEYS[1]
local keyCount = KEYS[2]
local kStartIndex = 2;

if (redis.call('del', uniqueKey) == false) then
    return -1;
end

for i = 0, keyCount - 1, 1 do
    local k = KEYS[kStartIndex + i * 3 + 1];
    local count = KEYS[kStartIndex + i * 3 + 2];
    local expire = KEYS[kStartIndex + i * 3 + 3];
    redis.call('decrby', k, count);
    redis.call('expire', k, expire);
end

return 1;