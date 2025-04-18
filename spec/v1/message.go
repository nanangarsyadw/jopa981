package v1

// MessageKind message kind
type MessageKind string

const (
	// MessageReport report message kind
	MessageReport MessageKind = "report"
	// MessageDesire desire message kind
	MessageDesire MessageKind = "desire"
	// MessageKeep keep alive message kind
	MessageKeep MessageKind = "keepalive"
	// MessageCMD command message kind
	MessageCMD MessageKind = "cmd"
	// MessageData data message kind
	MessageData MessageKind = "data"
	// MessageError error message kind
	MessageError MessageKind = "error"
	// Message response message kind
	MessageResponse MessageKind = "response"
	// MessageDelta delta message kind
	MessageDelta MessageKind = "delta"
	// MessageEvent event message = "event"
	MessageEvent MessageKind = "event"
	// MessageNodeProps node props message kind
	MessageNodeProps MessageKind = "nodeProps"
	// MessageDevices devices message kind
	MessageDevices MessageKind = "devices"
	// MessageDeviceEvent device event message kind
	MessageDeviceEvent MessageKind = "deviceEvent"
	// MessageReport device report message kind
	MessageDeviceReport MessageKind = "deviceReport"
	// MessageDesire device desire message kind
	MessageDeviceDesire MessageKind = "deviceDesire"
	// MessageDesire device delta message kind
	MessageDeviceDelta MessageKind = "deviceDelta"
	// MessageDeviceLatestProperty device get property message kind
	MessageDevicePropertyGet MessageKind = "thing.property.get"
	// MessageReport device event report message kind
	MessageDeviceEventReport MessageKind = "thing.event.post"
	// MessageReport device lifecycle report message kind
	MessageDeviceLifecycleReport MessageKind = "thing.lifecycle.post"
	// MessageMC get/send mc info
	MessageMC MessageKind = "mc"
	// MessageSTS get/send s3 sts info
	MessageSTS MessageKind = "sts"

	// MessageCommandConnect start remote debug command
	MessageCommandConnect = "connect"
	// MessageCommandDisconnect stop remote debug command
	MessageCommandDisconnect = "disconnect"
	// MessageCommandLogs logs
	MessageCommandLogs = "logs"
	// MessageCommandNodeLabel label the edge cluster nodes
	MessageCommandNodeLabel = "nodeLabel"
	// MessageCommandMultiNodeLabels label multiple nodes
	MessageCommandMultiNodeLabels = "multiNodeLabels"
	// MessageCommandDescribe describe
	MessageCommandDescribe = "describe"
	// MessageRPC call edge app
	MessageRPC = "rpc"
	// MessageAgent get or set agent stat
	MessageAgent = "agent"
	// MessageRPCMqtt push message to broker
	MessageRPCMqtt = "rpcMqtt"
	// MessageMCAppList get mc app list
	MessageMCAppList = "mcAppList"
	// MessageMCAppInfo get mc app info
	MessageMCAppInfo = "mcAppInfo"
	// MessageSTSTypeMinio get minio sts
	MessageSTSTypeMinio = "minio"
)

// Message general structure for http and ws sync
type Message struct {
	Kind     MessageKind       `yaml:"kind" json:"kind"`
	Metadata map[string]string `yaml:"meta" json:"meta"`
	Content  LazyValue         `yaml:"content" json:"content"`
}
