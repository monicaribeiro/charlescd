package plugin

import (
	"compass/internal/util"
	"encoding/json"
	"errors"
	"io"
)

type Plugin struct {
	util.BaseModel
	Name string `json:"name"`
	Src  string `json:"src"`
}

func (plugin Plugin) Validate() error {
	if plugin.Name == "" {
		return errors.New("Name is required")
	}

	if plugin.Src == "" {
		return errors.New("Source path is required")
	}

	return nil
}

func (main Main) Parse(plugin io.ReadCloser) (Plugin, error) {
	var newPlugin *Plugin
	err := json.NewDecoder(plugin).Decode(&newPlugin)
	if err != nil {
		return Plugin{}, err
	}
	return *newPlugin, nil
}

func (main Main) FindAll() ([]Plugin, error) {
	plugins := []Plugin{}
	db := main.db.Find(&plugins)
	if db.Error != nil {
		return []Plugin{}, db.Error
	}
	return plugins, nil
}

func (main Main) Save(plugin Plugin) (Plugin, error) {
	db := main.db.Create(&plugin)
	if db.Error != nil {
		return Plugin{}, db.Error
	}
	return plugin, nil
}

func (main Main) FindById(id string) (Plugin, error) {
	plugin := Plugin{}
	db := main.db.Where("id = ?", id).First(&plugin)
	if db.Error != nil {
		return Plugin{}, db.Error
	}
	return plugin, nil
}

func (main Main) Update(id string, plugin Plugin) (Plugin, error) {
	db := main.db.Where("id = ?", id).Update(&plugin)
	if db.Error != nil {
		return Plugin{}, db.Error
	}
	return plugin, nil
}

func (main Main) Remove(id string) error {
	db := main.db.Where("id = ?", id).Delete(Plugin{})
	if db.Error != nil {
		return db.Error
	}
	return nil
}