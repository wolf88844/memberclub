local uniqueKey = KEYS[1]
local keyCount = KEYS[2]
local kStartIndex = 2;

if (redis.call('del', uniqueKey) == false) then
    return -1;
end

for i = 0, keyCount - 1, 1 do
    local k = KEYS[kStartIndex + i * 2 + 1];
    local count = KEYS[kStartIndex + i * 2 + 2];
    redis.call('decrby', k, count);
end

return 1;