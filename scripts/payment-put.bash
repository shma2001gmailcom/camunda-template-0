#!/usr/bin/env bash

#!/usr/bin/env bash

echo `curl \
-H "Content-Type: application/json" \
-X PUT \
-d '{}' \
http://localhost:8100/api/payment/v2
`
